import customerProductReducer from './Reducer';
import {
  FIND_PRODUCTS_BY_CATEGORY_REQUEST,
  FIND_PRODUCTS_BY_CATEGORY_SUCCESS,
  FIND_PRODUCTS_BY_CATEGORY_FAILURE,
  FIND_PRODUCT_BY_ID_REQUEST,
  ADD_PRODUCT,
  FIND_PRODUCT_BY_ID_SUCCESS,
  FIND_PRODUCT_BY_ID_FAILURE,
  CREATE_PRODUCT_REQUEST,
  CREATE_PRODUCT_SUCCESS,
  CREATE_PRODUCT_FAILURE,
  UPDATE_PRODUCT_REQUEST,
  UPDATE_PRODUCT_SUCCESS,
  UPDATE_PRODUCT_FAILURE,
  DELETE_PRODUCT_REQUEST,
  DELETE_PRODUCT_FAILURE,
  DELETE_PRODUCT_SUCCESS,
  SEARCH_PRODUCTS_REQUEST,
  SEARCH_PRODUCTS_SUCCESS,
  SEARCH_PRODUCTS_FAILURE
} from './ActionTypes';

describe('customerProductReducer', () => {
  const initialState = {
    products: [],
    product: null,
    loading: false,
    error: null,
    deleteProduct: null,
    searchProducts: []
  };

  it('should return the initial state', () => {
    expect(customerProductReducer(undefined, {})).toEqual(initialState);
  });

  it('should handle ADD_PRODUCT', () => {
    const newProduct = { id: 1, name: 'New Product' };
    const action = { type: ADD_PRODUCT, payload: newProduct };
    const expectedState = {
      ...initialState,
      products: [newProduct]
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle FIND_PRODUCTS_BY_CATEGORY_REQUEST', () => {
    const action = { type: FIND_PRODUCTS_BY_CATEGORY_REQUEST };
    const expectedState = {
      ...initialState,
      loading: true,
      error: null,
      products: []
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle FIND_PRODUCTS_BY_CATEGORY_SUCCESS', () => {
    const products = [{ id: 1, name: 'Product 1' }];
    const action = { type: FIND_PRODUCTS_BY_CATEGORY_SUCCESS, payload: products };
    const expectedState = {
      ...initialState,
      products,
      loading: false
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle FIND_PRODUCTS_BY_CATEGORY_FAILURE', () => {
    const error = 'Error';
    const action = { type: FIND_PRODUCTS_BY_CATEGORY_FAILURE, payload: error };
    const expectedState = {
      ...initialState,
      loading: false,
      products: [],
      error
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle FIND_PRODUCT_BY_ID_REQUEST', () => {
    const action = { type: FIND_PRODUCT_BY_ID_REQUEST };
    const expectedState = {
      ...initialState,
      loading: true,
      error: null
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle FIND_PRODUCT_BY_ID_SUCCESS', () => {
    const product = { id: 1, name: 'Product 1' };
    const action = { type: FIND_PRODUCT_BY_ID_SUCCESS, payload: product };
    const expectedState = {
      ...initialState,
      product,
      loading: false
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle FIND_PRODUCT_BY_ID_FAILURE', () => {
    const error = 'Error';
    const action = { type: FIND_PRODUCT_BY_ID_FAILURE, payload: error };
    const expectedState = {
      ...initialState,
      loading: false,
      error
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle CREATE_PRODUCT_REQUEST', () => {
    const action = { type: CREATE_PRODUCT_REQUEST };
    const expectedState = {
      ...initialState,
      loading: true,
      error: null
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle CREATE_PRODUCT_SUCCESS', () => {
    const product = { id: 1, name: 'Product 1' };
    const action = { type: CREATE_PRODUCT_SUCCESS, payload: product };
    const expectedState = {
      ...initialState,
      loading: false,
      products: [product]
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle CREATE_PRODUCT_FAILURE', () => {
    const error = 'Error';
    const action = { type: CREATE_PRODUCT_FAILURE, payload: error };
    const expectedState = {
      ...initialState,
      loading: false,
      error
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle UPDATE_PRODUCT_REQUEST', () => {
    const action = { type: UPDATE_PRODUCT_REQUEST };
    const expectedState = {
      ...initialState,
      loading: true,
      error: null
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle UPDATE_PRODUCT_SUCCESS', () => {
    const initialStateWithProducts = {
      ...initialState,
      products: [{ id: 1, name: 'Product 1' }]
    };
    const updatedProduct = { id: 1, name: 'Updated Product' };
    const action = { type: UPDATE_PRODUCT_SUCCESS, payload: updatedProduct };
    const expectedState = {
      ...initialStateWithProducts,
      loading: false,
      products: [updatedProduct]
    };
    expect(customerProductReducer(initialStateWithProducts, action)).toEqual(expectedState);
  });

  it('should handle UPDATE_PRODUCT_FAILURE', () => {
    const error = 'Error';
    const action = { type: UPDATE_PRODUCT_FAILURE, payload: error };
    const expectedState = {
      ...initialState,
      loading: false,
      error
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle DELETE_PRODUCT_REQUEST', () => {
    const action = { type: DELETE_PRODUCT_REQUEST };
    const expectedState = {
      ...initialState,
      loading: true,
      error: null
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle DELETE_PRODUCT_SUCCESS', () => {
    const deleteProduct = { id: 1, name: 'Product 1' };
    const action = { type: DELETE_PRODUCT_SUCCESS, payload: deleteProduct };
    const expectedState = {
      ...initialState,
      loading: false,
      deleteProduct
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle DELETE_PRODUCT_FAILURE', () => {
    const error = 'Error';
    const action = { type: DELETE_PRODUCT_FAILURE, payload: error };
    const expectedState = {
      ...initialState,
      loading: false,
      error
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle SEARCH_PRODUCTS_REQUEST', () => {
    const action = { type: SEARCH_PRODUCTS_REQUEST };
    const expectedState = {
      ...initialState,
      loading: true,
      error: null,
      searchProducts: []
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle SEARCH_PRODUCTS_SUCCESS', () => {
    const searchProducts = [{ id: 1, name: 'Product 1' }];
    const action = { type: SEARCH_PRODUCTS_SUCCESS, payload: searchProducts };
    const expectedState = {
      ...initialState,
      loading: false,
      searchProducts
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });

  it('should handle SEARCH_PRODUCTS_FAILURE', () => {
    const error = 'Error';
    const action = { type: SEARCH_PRODUCTS_FAILURE, payload: error };
    const expectedState = {
      ...initialState,
      loading: false,
      error
    };
    expect(customerProductReducer(initialState, action)).toEqual(expectedState);
  });
});
