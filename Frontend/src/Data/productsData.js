/* istanbul ignore file */ 

import { useQuery } from '@tanstack/react-query';
import axios from 'axios';

const fetchProductsByCategory = async ({ queryKey }) => {
  const [_,categoryName, page, size] = queryKey;
  const response = await axios.get(`http://localhost:8082/api/products/getbycategory/${categoryName}`, {
    params: { page, size },
  });
  return response.data;
};

export const useProductsByCategory = (categoryName, page, size) => {
  return useQuery({
    queryKey: ['products', categoryName, page, size],
    queryFn: fetchProductsByCategory,
    staleTime: 30000,
    cacheTime: 60000,
    refetchInterval: 120000,
  });
};
