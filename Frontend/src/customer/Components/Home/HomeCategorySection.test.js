import React from 'react';
import { render, screen } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import HomeCategorySection from './HomeCategorySection.jsx';

jest.mock('./HomeCategoryCard', () => jest.fn(() => <div>Mocked HomeCategoryCard</div>));

describe('HomeCategorySection', () => {
  const dataMock = [
    { id: 1, name: 'Category 1', description: 'Description 1', imageUrl: '/image1.jpg' },
    { id: 2, name: 'Category 2', description: 'Description 2', imageUrl: '/image2.jpg' },
    { id: 3, name: 'Category 3', description: 'Description 3', imageUrl: '/image3.jpg' },
  ];

  it('renders correctly with the provided data', () => {
    render(
      <Router>
        <HomeCategorySection data={dataMock} />
      </Router>
    );

    expect(screen.getByText('Mocked HomeCategoryCard')).toBeInTheDocument();
  });

  it('renders a HomeCategoryCard for each item in the data', () => {
    render(
      <Router>
        <HomeCategorySection data={dataMock} />
      </Router>
    );

    const categoryCards = screen.getAllByText('Mocked HomeCategoryCard');
    expect(categoryCards).toHaveLength(dataMock.length);
  });
});
