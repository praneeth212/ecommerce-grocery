import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter as Router, useLocation } from 'react-router-dom';
import Product from './Product.jsx'; 
import { useProductsByCategory } from '../../../../Data/productsData'; 

jest.mock('../../../../Data/productsData');
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: jest.fn(),
  useParams: () => ({ categoryName: 'Test Category' }),
  useLocation: jest.fn(),
}));

describe('Product Component', () => {
  const mockNavigate = jest.fn();
  const productsMockData = {
    content: [
      {
        productItemId: 1,
        prodName: 'Test Product',
        brand: 'Test Brand',
        productImg: '/test-image.jpg',
        price: 100,
        salePrice: 80,
        discountPercentage: 20,
        unitName: 'Unit',
        quantityInStock: 10
      }
    ],
    totalPages: 1,
  };

  beforeEach(() => {
    useProductsByCategory.mockReturnValue({
      data: productsMockData,
      isLoading: false,
    });
    useLocation.mockReturnValue({
      search: '',
      pathname: '/products/Test Category'
    });
    jest.clearAllMocks();
  });

  it('renders product details correctly', async () => {
    render(
      <Router>
        <Product />
      </Router>
    );

    expect(screen.getByText('Test Category Products')).toBeInTheDocument();
    await waitFor(() => {
      expect(screen.getByText('Test Product')).toBeInTheDocument();
    });
    expect(screen.getByText('Test Brand')).toBeInTheDocument();
    expect(screen.getByText('₹80.00')).toBeInTheDocument();
    expect(screen.getByText('₹100.00')).toBeInTheDocument();
    expect(screen.getByText('20.00% off')).toBeInTheDocument();
    expect(screen.getByText('10 in stock')).toBeInTheDocument();
  });

  it('displays loader while fetching data', async () => {
    useProductsByCategory.mockReturnValue({
      data: null,
      isLoading: true,
    });

    render(
      <Router>
        <Product />
      </Router>
    );

    expect(screen.getByRole('progressbar')).toBeInTheDocument();
  });

  it('handles pagination change', async () => {
    render(
      <Router>
        <Product />
      </Router>
    );

    const pagination = screen.getByRole('navigation');
    expect(pagination).toBeInTheDocument();

    fireEvent.click(screen.getByRole('button', { name: /2/i }));
    await waitFor(() => {
      expect(mockNavigate).toHaveBeenCalledWith({
        search: '?page=2',
      });
    });
  });

  it('handles checkbox filter change', async () => {
    render(
      <Router>
        <Product />
      </Router>
    );

    const checkbox = screen.getByLabelText('100 Gms');
    fireEvent.click(checkbox);

    await waitFor(() => {
      expect(mockNavigate).toHaveBeenCalledWith({
        search: '?weight=100 Gms',
      });
    });
  });

  it('handles radio filter change', async () => {
    render(
      <Router>
        <Product />
      </Router>
    );

    const radio = screen.getByLabelText('In Stock');
    fireEvent.click(radio);

    await waitFor(() => {
      expect(mockNavigate).toHaveBeenCalledWith({
        search: '?stock=in_stock',
      });
    });
  });

  it('shows "No products found" when there are no products', async () => {
    useProductsByCategory.mockReturnValue({
      data: { content: [], totalPages: 1 },
      isLoading: false,
    });

    render(
      <Router>
        <Product />
      </Router>
    );

    await waitFor(() => {
      expect(screen.getByText('No products found in this category.')).toBeInTheDocument();
    });
  });
});
