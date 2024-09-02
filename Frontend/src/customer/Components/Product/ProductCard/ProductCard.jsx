import React from 'react';
import { useNavigate } from "react-router-dom";
import PropTypes from 'prop-types';

const ProductCard = ({ product }) => {
  const {
    productItemId,
    prodName,
    brand,
    productImg,
    price,
    salePrice,
    discountPercentage,
    unitName,
    quantityInStock
  } = product;
  const navigate = useNavigate();

  const handleNavigate = () => {
    navigate(`/product/${productItemId}`);
  };


  return (
    <div 
      onClick={handleNavigate} 
      role="button" 
      className='w-[15rem] border hover m-3 transition-all cursor-pointer hover:scale-105'
    >
      <div className='h-[20rem]'>
        <img className='h-full w-full object-cover object-left-top' src={`http://localhost:8082${productImg}`} alt={prodName} />
      </div>
      <div className='textPart bg-white p-3'>
        <div>
          <p className='font-bold opacity-60'>{brand}</p>
          <p className=''>{prodName}</p>
        </div>
        <div className='flex space-x-2 items-center'>
          <p className='font-semibold'>₹{salePrice.toFixed(2)}</p>
          <p className='opacity-50 line-through'>₹{price.toFixed(2)}</p>
          <p className='text-green-600 font-semibold'>{discountPercentage.toFixed(2)}% off</p>
        </div>
        <p className='text-sm opacity-60'>{unitName}</p>
        <p className={`text-sm ${quantityInStock === 0 ? 'text-red-600' : 'opacity-60'}`}>
          {quantityInStock === 0 ? 'Out of stock' : `${quantityInStock} in stock`}
        </p>
      </div>
    </div>
  );
};

ProductCard.propTypes = {
  product: PropTypes.shape({
    productItemId: PropTypes.number.isRequired,
    prodName: PropTypes.string.isRequired,
    brand: PropTypes.string.isRequired,
    productImg: PropTypes.string.isRequired,
    price: PropTypes.number.isRequired,
    salePrice: PropTypes.number.isRequired,
    discountPercentage: PropTypes.number.isRequired,
    unitName: PropTypes.string.isRequired,
    quantityInStock: PropTypes.number.isRequired
  }).isRequired
};

export default ProductCard;
