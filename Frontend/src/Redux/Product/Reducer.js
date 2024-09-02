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
    FETCH_PRODUCT_REQUEST,
    FETCH_PRODUCT_SUCCESS,
    FETCH_PRODUCT_FAILURE,
    FIND_PRODUCTS_SUCCESS,
    FIND_PRODUCTS_FAILURE,
    FIND_PRODUCTS_REQUEST,
    SEARCH_PRODUCTS_REQUEST,
    SEARCH_PRODUCTS_SUCCESS,
    SEARCH_PRODUCTS_FAILURE
  } from "./ActionTypes";
  
  const initialState = {
    products: [],
    product: null,
    loading: false,
    error: null,
    deleteProduct: null,
    searchProducts: []
  };
  
  const customerProductReducer = (state = initialState, action) => {
    switch (action.type) {
      case ADD_PRODUCT:
        return { ...state, products: [...state.products, action.payload] };
      case FIND_PRODUCTS_BY_CATEGORY_REQUEST:
        return { ...state, loading: true, error: null, products: [] };
      case FIND_PRODUCTS_BY_CATEGORY_SUCCESS:
        return { ...state, products: action.payload, loading: false };
      case FIND_PRODUCTS_BY_CATEGORY_FAILURE:
        return { ...state, loading: false, products: [], error: action.payload };
      case FIND_PRODUCT_BY_ID_REQUEST:
        return { ...state, loading: true, error: null };
      case FIND_PRODUCT_BY_ID_SUCCESS:
        return { ...state, product: action.payload, loading: false };
      case FIND_PRODUCT_BY_ID_FAILURE:
        return { ...state, loading: false, error: action.payload };
      case CREATE_PRODUCT_REQUEST:
        return { ...state, loading: true, error: null };
      case FIND_PRODUCTS_REQUEST:
        return { ...state, loading: true, error: null };
      case FIND_PRODUCTS_SUCCESS:
        return { ...state, loading: false, products: action.payload };
      case FIND_PRODUCTS_FAILURE:
        return { ...state, loading: false, error: action.payload };
      case FETCH_PRODUCT_REQUEST:
        return { ...state, loading: true };
      case FETCH_PRODUCT_SUCCESS:
        return { ...state, loading: false, products: action.payload, error: null };
      case FETCH_PRODUCT_FAILURE:
        return { ...state, loading: false, products: [], error: action.payload };
      case CREATE_PRODUCT_SUCCESS:
        return { ...state, loading: false, products: [...state.products, action.payload] };
      case CREATE_PRODUCT_FAILURE:
        return { ...state, loading: false, error: action.payload };
      case UPDATE_PRODUCT_REQUEST:
        return { ...state, loading: true, error: null };
      case UPDATE_PRODUCT_SUCCESS:
        return {
          ...state,
          loading: false,
          products: state.products.map((product) =>
            product.id === action.payload.id ? action.payload : product
          ),
        };
      case UPDATE_PRODUCT_FAILURE:
        return { ...state, loading: false, error: action.payload };
      case DELETE_PRODUCT_REQUEST:
        return { ...state, loading: true, error: null };
      case DELETE_PRODUCT_SUCCESS:
        return { ...state, loading: false, deleteProduct: action.payload };
      case DELETE_PRODUCT_FAILURE:
        return { ...state, loading: false, error: action.payload };
      case SEARCH_PRODUCTS_REQUEST:
        return { ...state, loading: true, error: null, searchProducts: [] };
      case SEARCH_PRODUCTS_SUCCESS:
        return { ...state, loading: false, searchProducts: action.payload };
      case SEARCH_PRODUCTS_FAILURE:
        return { ...state, loading: false, error: action.payload };
      default:
        return state;
    }
  };

  export default customerProductReducer;
