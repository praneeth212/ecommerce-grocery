import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import axios from 'axios';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import ProductDetails from './ProductDetails.jsx';

jest.mock('axios');

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useParams: jest.fn(),
}));

describe('ProductDetails', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('renders loading state initially', () => {
    require('react-router-dom').useParams.mockReturnValue({ productItemId: '1' });

    render(
      <MemoryRouter initialEntries={['/product/1']}>
        <Routes>
          <Route path='/product/:productItemId' element={<ProductDetails />} />
        </Routes>
      </MemoryRouter>
    );

    expect(screen.getByText('Loading...')).toBeInTheDocument();
  });

  it('renders error state on fetch error', async () => {
    require('react-router-dom').useParams.mockReturnValue({ productItemId: '1' });
    axios.get.mockRejectedValueOnce(new Error('Network Error'));

    render(
      <MemoryRouter initialEntries={['/product/1']}>
        <Routes>
          <Route path='/product/:productItemId' element={<ProductDetails />} />
        </Routes>
      </MemoryRouter>
    );

    await waitFor(() => expect(screen.getByText('Error loading product details')).toBeInTheDocument());
  });

  it('renders product details on successful fetch', async () => {
    require('react-router-dom').useParams.mockReturnValue({ productItemId: '1' });

    const mockData = {
      productItemId: 1,
      prodName: 'Test Product',
      categoryName: 'Test Category',
      brand: 'Test Brand',
      salePrice: 100,
      price: 150,
      discountPercentage: 33.33,
      productImg: '/test-image.jpg',
      description: 'Test Description',
      quantityInStock: 10,
    };

    axios.get.mockResolvedValueOnce({ data: mockData });

    render(
      <MemoryRouter initialEntries={['/product/1']}>
        <Routes>
          <Route path='/product/:productItemId' element={<ProductDetails />} />
        </Routes>
      </MemoryRouter>
    );

    await waitFor(() => expect(screen.getAllByText('Test Product')).toHaveLength(2));

    const productTitle = screen.getAllByText('Test Product')[1];
    expect(productTitle).toBeInTheDocument();

    expect(screen.getByText('Test Brand')).toBeInTheDocument();
    expect(screen.getByText('Test Description')).toBeInTheDocument();
    expect(screen.getByText('₹100')).toBeInTheDocument();
    expect(screen.getByText('33.33% off')).toBeInTheDocument();
  });
});
