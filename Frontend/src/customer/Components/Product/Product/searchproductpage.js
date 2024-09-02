import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useParams } from 'react-router-dom';
import { searchProducts } from '../../../../Redux/Product/Action';
import ProductCard from '../ProductCard/ProductCard';

const SearchProductPage = () => {
  const { query } = useParams();
  const dispatch = useDispatch();
  const { searchProducts: products, isLoading, isError } = useSelector((state) => state.customersProduct);

  useEffect(() => {
    if (query) {
      dispatch(searchProducts(query));
    }
  }, [query, dispatch]);

  return (
    <div className="container mx-auto mt-8 p-4">
      <h1 className="text-3xl font-bold mb-4">Search Results for "{query}"</h1>
      {isLoading && <div className="text-center">Loading...</div>}
      {isError && <div className="text-center text-red-500">Error fetching products</div>}

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {products.map((product) => (
          <ProductCard key={product.productItemId} product={product} />
        ))}
      </div>
    </div>
  );
};

export default SearchProductPage;
