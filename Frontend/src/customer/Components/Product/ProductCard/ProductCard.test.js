import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import ProductCard from './ProductCard.jsx'; 

const product = {
  productItemId: 1,
  prodName: 'Test Product',
  brand: 'Test Brand',
  productImg: '/test-image.jpg',
  price: 100,
  salePrice: 80,
  discountPercentage: 20,
  unitName: 'Unit',
  quantityInStock: 10
};

describe('ProductCard', () => {
  it('renders product details correctly', () => {
    render(
      <Router>
        <ProductCard product={product} />
      </Router>
    );

    const img = screen.getByAltText('Test Product');
    expect(img).toBeInTheDocument();
    expect(img).toHaveAttribute('src', 'http://localhost:8082/test-image.jpg');

    expect(screen.getByText('Test Brand')).toBeInTheDocument();
    expect(screen.getByText('Test Product')).toBeInTheDocument();

    expect(screen.getByText('₹80.00')).toBeInTheDocument();
    expect(screen.getByText('₹100.00')).toBeInTheDocument();
    expect(screen.getByText('20.00% off')).toBeInTheDocument();

    expect(screen.getByText('Unit')).toBeInTheDocument();
    expect(screen.getByText('10 in stock')).toBeInTheDocument();
  });

  it('shows "Out of stock" when quantityInStock is 0', () => {
    const outOfStockProduct = { ...product, quantityInStock: 0 };
    render(
      <Router>
        <ProductCard product={outOfStockProduct} />
      </Router>
    );

    expect(screen.getByText('Out of stock')).toBeInTheDocument();
  });

  it('navigates to the product details page on click', () => {
    const { container } = render(
      <Router>
        <ProductCard product={product} />
      </Router>
    );

    const card = container.querySelector('.w-[15rem]');
    expect(card).toBeInTheDocument();

    fireEvent.click(card);
  });
});
