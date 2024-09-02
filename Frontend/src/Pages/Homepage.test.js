import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import Homepage from './Homepage.jsx'; 
import { useCategoryData } from '../Data/categoryData';

jest.mock('../Data/categoryData');
jest.mock('../customer/Components/Home/HomeCategorySection', () => () => <div>HomeCategorySection Mock</div>);

describe('Homepage', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('renders loading state', () => {
    useCategoryData.mockReturnValue({ data: null, error: null, isLoading: true });

    render(<Homepage />);

    expect(screen.getByText('Loading...')).toBeInTheDocument();
  });

  it('renders error state', () => {
    useCategoryData.mockReturnValue({ data: null, error: 'Error loading categories', isLoading: false });

    render(<Homepage />);

    expect(screen.getByText('Error loading categories')).toBeInTheDocument();
  });

  it('renders homepage with category data', () => {
    const mockData = [
      {
        id: 1,
        name: 'Category 1',
        childCategories: [{ id: 101, name: 'Child Category 1' }],
      },
      {
        id: 2,
        name: 'Category 2',
        childCategories: [{ id: 102, name: 'Child Category 2' }],
      },
    ];

    useCategoryData.mockReturnValue({ data: mockData, error: null, isLoading: false });

    render(<Homepage />);

    // Check for the category section headers
    expect(screen.getByText('Browse categories')).toBeInTheDocument();
    expect(screen.getByText('Category 1')).toBeInTheDocument();
    expect(screen.getByText('Category 2')).toBeInTheDocument();

    expect(screen.getAllByText('HomeCategorySection Mock').length).toBe(2);
  });
});
