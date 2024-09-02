import axios from "axios";
 
import {
  FIND_PRODUCT_BY_ID_REQUEST,
  FIND_PRODUCT_BY_ID_SUCCESS,
  FIND_PRODUCT_BY_ID_FAILURE,
  ADD_PRODUCT,

  DELETE_PRODUCT_REQUEST,
  DELETE_PRODUCT_SUCCESS,
  DELETE_PRODUCT_FAILURE,

  FETCH_PRODUCT_REQUEST,
  FETCH_PRODUCT_SUCCESS,
  FETCH_PRODUCT_FAILURE,

  FIND_PRODUCTS_REQUEST,
  FIND_PRODUCTS_SUCCESS,
  FIND_PRODUCTS_FAILURE,

  SEARCH_PRODUCTS_REQUEST,
  SEARCH_PRODUCTS_SUCCESS,
  SEARCH_PRODUCTS_FAILURE,
} from "./ActionTypes";
  
export const fetchProductRequest = () => ({
  type: FETCH_PRODUCT_REQUEST,
});
 
export const fetchProductSuccess = (product) => ({
  type: FETCH_PRODUCT_SUCCESS,
  payload: product,
});
 
export const fetchProductFailure = (error) => ({
  type: FETCH_PRODUCT_FAILURE,
  payload: error,
});

export const searchProductsRequest = () => ({
  type: SEARCH_PRODUCTS_REQUEST,
});

export const searchProductsSuccess = (products) => ({
  type: SEARCH_PRODUCTS_SUCCESS,
  payload: products,
});

export const searchProductsFailure = (error) => ({
  type: SEARCH_PRODUCTS_FAILURE,
  payload: error,
});



export const updateStockQuantity = (productItemId, newQuantity) => async dispatch => {
  try {
    const response = await axios.put(
      `http://localhost:8082/api/productitems/quantity/${productItemId}/${newQuantity}`
    );
    dispatch({ type: 'UPDATE_STOCK_QUANTITY', payload: response.data });
  } catch (error) {
    console.error('Error updating stock quantity:', error);
    dispatch({ type: 'UPDATE_STOCK_QUANTITY_ERROR', payload: error });
  }
};

export const addProduct = product => async dispatch => {
  try {
      const response = await axios.post(`http://localhost:8082/api/products/create`, product, {
        headers:{
          'Content-Type':'multipart/form-data'
        }
      });
      dispatch({ type: ADD_PRODUCT, payload: response.data });
  } catch (error) {
      console.error('Error adding product:', error);
  }
};  
 
 
export const fetchAllProducts = () => async (dispatch) => {
    dispatch({ type: FIND_PRODUCTS_REQUEST });
    try {
      const response = await axios.get(`http://localhost:8082/api/products/details`);
      dispatch({ type: FIND_PRODUCTS_SUCCESS, payload: response.data });
    } catch (error) {
      dispatch({ type: FIND_PRODUCTS_FAILURE, payload: error.message });
    }
  };

export const findProducts = (data) => async (dispatch) => {
  dispatch({ type: FIND_PRODUCTS_REQUEST });
  try {
    const response = await axios.post('http://localhost:8082/api/products/details', data);
    dispatch({ type: FIND_PRODUCTS_SUCCESS, payload: response.data });
  } catch (error) {
    dispatch({ type: FIND_PRODUCTS_FAILURE, payload: error.message });
  }
};

export const fetchProductsByCategory = (categoryName) => async dispatch => {
  dispatch({ type: FETCH_PRODUCT_REQUEST });
  try {
    const response = await axios.get(`http://localhost:8082/api/products/getbycategory/${categoryName}`);
    console.log(response.data); 
    dispatch({ type: FETCH_PRODUCT_SUCCESS, payload: response.data });
  } catch (error) {
    dispatch({ type: FETCH_PRODUCT_FAILURE, payload: error.message });
  }
};

export const findProductById = (reqData) => async (dispatch) => {
  try {
    dispatch({ type: FIND_PRODUCT_BY_ID_REQUEST });
 
    const { data } = await axios.get(`http://localhost:8082/api/products/getdetail/${reqData.productItemId}`);
 
    console.log("products by  id : ", data);
    dispatch({
      type: FIND_PRODUCT_BY_ID_SUCCESS,
      payload: data,
    });
  } catch (error) {
    dispatch({
      type: FIND_PRODUCT_BY_ID_FAILURE,
      payload:
        error.response && error.response.data.message
          ? error.response.data.message
          : error.message,
    });
  }
};
 
 
export const deleteProduct = (productItemId) => async (dispatch) => {
  console.log("delete product action",productItemId)
  try {
    dispatch({ type: DELETE_PRODUCT_REQUEST });
 
    let {data}=await axios.delete(`http://localhost:8082/api/productitems/delete/${productItemId}`);
 
    dispatch({
      type: DELETE_PRODUCT_SUCCESS,
      payload: data,
    });
 
    console.log("product deleted ",data)
  } catch (error) {
    console.log("catch error ",error)
    dispatch({
      type: DELETE_PRODUCT_FAILURE,
      payload:
        error.response && error.response.data.message
          ? error.response.data.message
          : error.message,
    });
  }
};


export const searchProducts = (query) => {
  return async (dispatch) => {
    dispatch(searchProductsRequest());
    try {
      const response = await axios.get(`http://localhost:8082/api/products/search/${query}`);
      dispatch(searchProductsSuccess(response.data));
    } catch (error) {
      dispatch(searchProductsFailure(error.message));
    }
  };
};