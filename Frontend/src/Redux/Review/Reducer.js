import {
  CREATE_REVIEW_REQUEST,
  CREATE_REVIEW_SUCCESS,
  CREATE_REVIEW_FAILURE,
  FETCH_REVIEWS_REQUEST,
  FETCH_REVIEWS_SUCCESS,
  FETCH_REVIEWS_FAILURE,
} from "./ActionType";
 
const initialState = {
  loading: false,
  reviews: [],
  error: null,
};
 
const reviewReducer = (state = initialState, action) => {
  switch (action.type) {
    case CREATE_REVIEW_REQUEST:
    case FETCH_REVIEWS_REQUEST:
      return { ...state, loading: true, error: null };
    case CREATE_REVIEW_SUCCESS:
      return {
        ...state,
        loading: false,
        reviews: [...state.reviews, action.payload],
      };
    case FETCH_REVIEWS_SUCCESS:
      return { ...state, loading: false, reviews: action.payload };
    case CREATE_REVIEW_FAILURE:
    case FETCH_REVIEWS_FAILURE:
      return { ...state, loading: false, error: action.payload };
    default:
      return state;
  }
};
 
export default reviewReducer;