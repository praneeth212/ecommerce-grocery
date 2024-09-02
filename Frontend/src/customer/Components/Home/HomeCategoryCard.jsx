import React from "react";
import { useNavigate } from "react-router-dom";

const HomeCategoryCard = ({ category }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/products/${category.name}`);
  };

  return (
    <div
      onClick={handleClick}
      className="cursor-pointer flex flex-col items-center bg-white rounded-lg shadow-lg overflow-hidden w-[15rem] h-[20rem] mx-3"
    >
      <div className="h-[12rem] w-full">
        <img
          className="object-cover object-center w-full h-full"
          src={`http://localhost:8082${category?.imageUrl}`}
          alt={category?.name}
        />
      </div>
      <div className="p-4 text-center flex flex-col justify-center h-[8rem] w-full">
        <h3 className="text-lg font-medium text-gray-900">{category?.name}</h3>
        <p className="mt-2 text-sm text-gray-500">{category?.description}</p>
      </div>
    </div>
  );
};

export default HomeCategoryCard;
