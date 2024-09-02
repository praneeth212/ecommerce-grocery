import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { Provider } from 'react-redux';
import configureMockStore from 'redux-mock-store';
import thunk from 'redux-thunk';
import { BrowserRouter as Router, useParams } from 'react-router-dom';
import SearchProductPage from './searchproductpage.js'; 
import { searchProducts } from '../../../../Redux/Product/Action';

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useParams: jest.fn(),
}));
jest.mock('../../../../Redux/Product/Action', () => ({
  searchProducts: jest.fn(),
}));

const mockStore = configureMockStore([thunk]);

describe('SearchProductPage Component', () => {
  let store;

  beforeEach(() => {
    store = mockStore({
      customersProduct: {
        searchProducts: [],
        isLoading: false,
        isError: false,
      },
    });
  });

  it('renders loading state initially', async () => {
    useParams.mockReturnValue({ query: 'test' });
    store = mockStore({
      customersProduct: {
        searchProducts: [],
        isLoading: true,
        isError: false,
      },
    });

    render(
      <Provider store={store}>
        <Router>
          <SearchProductPage />
        </Router>
      </Provider>
    );

    expect(screen.getByText('Loading...')).toBeInTheDocument();
  });

  it('renders error state', async () => {
    useParams.mockReturnValue({ query: 'test' });
    store = mockStore({
      customersProduct: {
        searchProducts: [],
        isLoading: false,
        isError: true,
      },
    });

    render(
      <Provider store={store}>
        <Router>
          <SearchProductPage />
        </Router>
      </Provider>
    );

    expect(screen.getByText('Error fetching products')).toBeInTheDocument();
  });

  it('renders search results', async () => {
    useParams.mockReturnValue({ query: 'test' });
    store = mockStore({
      customersProduct: {
        searchProducts: [
          {
            productItemId: 1,
            prodName: 'Test Product 1',
            brand: 'Test Brand 1',
            productImg: '/test-image-1.jpg',
            price: 100,
            salePrice: 80,
            discountPercentage: 20,
            unitName: 'Unit',
            quantityInStock: 10
          },
          {
            productItemId: 2,
            prodName: 'Test Product 2',
            brand: 'Test Brand 2',
            productImg: '/test-image-2.jpg',
            price: 200,
            salePrice: 150,
            discountPercentage: 25,
            unitName: 'Unit',
            quantityInStock: 5
          }
        ],
        isLoading: false,
        isError: false,
      },
    });

    render(
      <Provider store={store}>
        <Router>
          <SearchProductPage />
        </Router>
      </Provider>
    );

    await waitFor(() => {
      expect(screen.getByText('Search Results for "test"')).toBeInTheDocument();
      expect(screen.getByText('Test Product 1')).toBeInTheDocument();
      expect(screen.getByText('Test Product 2')).toBeInTheDocument();
    });
  });

  it('dispatches searchProducts action on mount', async () => {
    useParams.mockReturnValue({ query: 'test' });

    render(
      <Provider store={store}>
        <Router>
          <SearchProductPage />
        </Router>
      </Provider>
    );

    expect(searchProducts).toHaveBeenCalledWith('test');
  });
});
