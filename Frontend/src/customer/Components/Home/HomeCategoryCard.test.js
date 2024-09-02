import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import HomeCategoryCard from './HomeCategoryCard.jsx'; 
import '@testing-library/jest-dom'; 

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: jest.fn(),
}));

describe('HomeCategoryCard', () => {
  const mockNavigate = jest.fn();
  const categoryMock = {
    name: 'Test Category',
    description: 'This is a test category description',
    imageUrl: '/test-image.jpg',
  };

  beforeEach(() => {
    jest.clearAllMocks();
    jest.mock('react-router-dom', () => ({
      ...jest.requireActual('react-router-dom'),
      useNavigate: () => mockNavigate,
    }));
  });

  it('renders correctly with the provided category data', () => {
    render(
      <Router>
        <HomeCategoryCard category={categoryMock} />
      </Router>
    );

    expect(screen.getByText('Test Category')).toBeInTheDocument();
    expect(screen.getByText('This is a test category description')).toBeInTheDocument();
    expect(screen.getByAltText('Test Category')).toBeInTheDocument();
  });

  it('calls navigate with the correct path when clicked', () => {
    render(
      <Router>
        <HomeCategoryCard category={categoryMock} />
      </Router>
    );

    const card = screen.getByText('Test Category').closest('div');
    fireEvent.click(card);

    expect(mockNavigate).toHaveBeenCalledWith('/products/Test Category');
  });
});
