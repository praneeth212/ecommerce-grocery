import axios from 'axios';
import configureMockStore from 'redux-mock-store';
import thunk from 'redux-thunk';
import {
  updateStockQuantity,
  addProduct,
  fetchAllProducts,
  findProducts,
  fetchProductsByCategory,
  findProductById,
  deleteProduct,
  searchProducts
} from './Action';
import {
  FETCH_PRODUCT_REQUEST,
  FETCH_PRODUCT_SUCCESS,
  FETCH_PRODUCT_FAILURE,
  SEARCH_PRODUCTS_REQUEST,
  SEARCH_PRODUCTS_SUCCESS,
  SEARCH_PRODUCTS_FAILURE,
  FIND_PRODUCT_BY_ID_REQUEST,
  FIND_PRODUCT_BY_ID_SUCCESS,
  FIND_PRODUCT_BY_ID_FAILURE,
  ADD_PRODUCT,
  DELETE_PRODUCT_REQUEST,
  DELETE_PRODUCT_SUCCESS,
  DELETE_PRODUCT_FAILURE,
  FIND_PRODUCTS_REQUEST,
  FIND_PRODUCTS_SUCCESS,
  FIND_PRODUCTS_FAILURE,
} from './ActionTypes';

const middlewares = [thunk];
const mockStore = configureMockStore(middlewares);

jest.mock('axios');

describe('Action tests', () => {
  let store;

  beforeEach(() => {
    store = mockStore({});
  });

  it('dispatches FETCH_PRODUCT_SUCCESS after a successful fetchProductsByCategory API request', async () => {
    const categoryName = 'Electronics';
    const products = [{ id: 1, name: 'Product 1' }];
    axios.get.mockResolvedValue({ data: products });

    const expectedActions = [
      { type: FETCH_PRODUCT_REQUEST },
      { type: FETCH_PRODUCT_SUCCESS, payload: products },
    ];

    await store.dispatch(fetchProductsByCategory(categoryName));

    expect(store.getActions()).toEqual(expectedActions);
  });

  it('dispatches FETCH_PRODUCT_FAILURE after a failed fetchProductsByCategory API request', async () => {
    const categoryName = 'Electronics';
    const errorMessage = 'Network Error';
    axios.get.mockRejectedValue(new Error(errorMessage));

    const expectedActions = [
      { type: FETCH_PRODUCT_REQUEST },
      { type: FETCH_PRODUCT_FAILURE, payload: errorMessage },
    ];

    await store.dispatch(fetchProductsByCategory(categoryName));

    expect(store.getActions()).toEqual(expectedActions);
  });

  it('dispatches SEARCH_PRODUCTS_SUCCESS after a successful searchProducts API request', async () => {
    const query = 'test';
    const products = [{ id: 1, name: 'Product 1' }];
    axios.get.mockResolvedValue({ data: products });

    const expectedActions = [
      { type: SEARCH_PRODUCTS_REQUEST },
      { type: SEARCH_PRODUCTS_SUCCESS, payload: products },
    ];

    await store.dispatch(searchProducts(query));

    expect(store.getActions()).toEqual(expectedActions);
  });

  it('dispatches SEARCH_PRODUCTS_FAILURE after a failed searchProducts API request', async () => {
    const query = 'test';
    const errorMessage = 'Network Error';
    axios.get.mockRejectedValue(new Error(errorMessage));

    const expectedActions = [
      { type: SEARCH_PRODUCTS_REQUEST },
      { type: SEARCH_PRODUCTS_FAILURE, payload: errorMessage },
    ];

    await store.dispatch(searchProducts(query));

    expect(store.getActions()).toEqual(expectedActions);
  });

  it('dispatches FIND_PRODUCT_BY_ID_SUCCESS after a successful findProductById API request', async () => {
    const reqData = { productItemId: 1 };
    const product = { id: 1, name: 'Product 1' };
    axios.get.mockResolvedValue({ data: product });

    const expectedActions = [
      { type: FIND_PRODUCT_BY_ID_REQUEST },
      { type: FIND_PRODUCT_BY_ID_SUCCESS, payload: product },
    ];

    await store.dispatch(findProductById(reqData));

    expect(store.getActions()).toEqual(expectedActions);
  });

  it('dispatches FIND_PRODUCT_BY_ID_FAILURE after a failed findProductById API request', async () => {
    const reqData = { productItemId: 1 };
    const errorMessage = 'Network Error';
    axios.get.mockRejectedValue(new Error(errorMessage));

    const expectedActions = [
      { type: FIND_PRODUCT_BY_ID_REQUEST },
      { type: FIND_PRODUCT_BY_ID_FAILURE, payload: errorMessage },
    ];

    await store.dispatch(findProductById(reqData));

    expect(store.getActions()).toEqual(expectedActions);
  });

  it('dispatches FIND_PRODUCTS_SUCCESS after a successful findProducts API request', async () => {
    const data = { category: 'Electronics' };
    const products = [{ id: 1, name: 'Product 1' }];
    axios.post.mockResolvedValue({ data: products });

    const expectedActions = [
      { type: FIND_PRODUCTS_REQUEST },
      { type: FIND_PRODUCTS_SUCCESS, payload: products },
    ];

    await store.dispatch(findProducts(data));

    expect(store.getActions()).toEqual(expectedActions);
  });

  it('dispatches FIND_PRODUCTS_FAILURE after a failed findProducts API request', async () => {
    const data = { category: 'Electronics' };
    const errorMessage = 'Network Error';
    axios.post.mockRejectedValue(new Error(errorMessage));

    const expectedActions = [
      { type: FIND_PRODUCTS_REQUEST },
      { type: FIND_PRODUCTS_FAILURE, payload: errorMessage },
    ];

    await store.dispatch(findProducts(data));

    expect(store.getActions()).toEqual(expectedActions);
  });

  it('dispatches FIND_PRODUCTS_SUCCESS after a successful fetchAllProducts API request', async () => {
    const products = [{ id: 1, name: 'Product 1' }];
    axios.get.mockResolvedValue({ data: products });

    const expectedActions = [
      { type: FIND_PRODUCTS_REQUEST },
      { type: FIND_PRODUCTS_SUCCESS, payload: products },
    ];

    await store.dispatch(fetchAllProducts());

    expect(store.getActions()).toEqual(expectedActions);
  });

  it('dispatches FIND_PRODUCTS_FAILURE after a failed fetchAllProducts API request', async () => {
    const errorMessage = 'Network Error';
    axios.get.mockRejectedValue(new Error(errorMessage));

    const expectedActions = [
      { type: FIND_PRODUCTS_REQUEST },
      { type: FIND_PRODUCTS_FAILURE, payload: errorMessage },
    ];

    await store.dispatch(fetchAllProducts());

    expect(store.getActions()).toEqual(expectedActions);
  });

  it('dispatches DELETE_PRODUCT_SUCCESS after a successful deleteProduct API request', async () => {
    const productItemId = 1;
    const data = { success: true };
    axios.delete.mockResolvedValue({ data });

    const expectedActions = [
      { type: DELETE_PRODUCT_REQUEST },
      { type: DELETE_PRODUCT_SUCCESS, payload: data },
    ];

    await store.dispatch(deleteProduct(productItemId));

    expect(store.getActions()).toEqual(expectedActions);
  });

  it('dispatches DELETE_PRODUCT_FAILURE after a failed deleteProduct API request', async () => {
    const productItemId = 1;
    const errorMessage = 'Network Error';
    axios.delete.mockRejectedValue(new Error(errorMessage));

    const expectedActions = [
      { type: DELETE_PRODUCT_REQUEST },
      { type: DELETE_PRODUCT_FAILURE, payload: errorMessage },
    ];

    await store.dispatch(deleteProduct(productItemId));

    expect(store.getActions()).toEqual(expectedActions);
  });

  it('dispatches UPDATE_STOCK_QUANTITY after a successful updateStockQuantity API request', async () => {
    const productItemId = 1;
    const newQuantity = 10;
    const data = { id: 1, quantity: 10 };
    axios.put.mockResolvedValue({ data });

    const expectedActions = [
      { type: 'UPDATE_STOCK_QUANTITY', payload: data },
    ];

    await store.dispatch(updateStockQuantity(productItemId, newQuantity));

    expect(store.getActions()).toEqual(expectedActions);
  });

  it('dispatches ADD_PRODUCT after a successful addProduct API request', async () => {
    const product = { name: 'New Product' };
    const data = { id: 1, name: 'New Product' };
    axios.post.mockResolvedValue({ data });

    const expectedActions = [
      { type: ADD_PRODUCT, payload: data },
    ];

    await store.dispatch(addProduct(product));

    expect(store.getActions()).toEqual(expectedActions);
  });

  it('logs error after a failed addProduct API request', async () => {
    const product = { name: 'New Product' };
    const errorMessage = 'Network Error';
    axios.post.mockRejectedValue(new Error(errorMessage));

    console.error = jest.fn(); // mock console.error

    await store.dispatch(addProduct(product));

    expect(console.error).toHaveBeenCalledWith('Error adding product:', new Error(errorMessage));
  });
});
