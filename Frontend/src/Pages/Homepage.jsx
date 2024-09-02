import React from "react";
import HomeCarousel from "../customer/Components/Carousel/HomeCarousel";
import { homeCarouselData } from "../customer/Components/Carousel/HomeCaroselData";
import { useCategoryData } from "../Data/categoryData";
import HomeCategorySection from "../customer/Components/Home/HomeCategorySection";

const Homepage = () => {
  const { data, error, isLoading } = useCategoryData();

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error loading categories</div>;

  return (
    <div className="">
      <HomeCarousel images={homeCarouselData} />
      <div className="space-y-10 py-20">
        <h1 className="text-3xl font-bold text-gray-900 px-5">Browse categories</h1>
        {data?.map((category) => (
          <div key={category.id}>
            <h2 className="text-2xl font-extrabold text-gray-900 py-5 px-5">{category.name}</h2>
            <HomeCategorySection data={category.childCategories} section={category.name} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Homepage;
