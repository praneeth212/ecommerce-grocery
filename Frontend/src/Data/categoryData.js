/* istanbul ignore file */ 

import { useQuery } from '@tanstack/react-query';
import axios from 'axios';

const fetchCategories = async () => {
  const response = await axios.get('http://localhost:8082/api/categories/categories-with-children');
  return response.data;
};

export const useCategoryData = () => {
  return useQuery({
    queryKey: ['categories'],
    queryFn: fetchCategories,
    staleTime: 30000,
    cacheTime: 60000,
    refetchInterval: 120000,
  });
};
