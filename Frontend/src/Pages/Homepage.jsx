import React from "react";
import HomeCarousel from "../customer/Components/Carousel/HomeCarousel";
import { homeCarouselData } from "../customer/Components/Carousel/HomeCaroselData";
import HomeProductSection from "../customer/Components/Home/HomeProductSection";
import {beverage1} from "../Data/Bevarages/beverage1";
import {fruits1} from "../Data/Fruitsandvegatables/fruits1";
import {beauty1} from "../Data/Beauty/beautyandhygine";
import { snacks1 } from "../Data/Snacks/snacks";
import RateProduct from "../customer/Components/ReviewProduct/RateProduct";
import ProductReviewCard from "../customer/Components/Product/ProductDetails/ProductReviewCard";
import ProfileNavigation from "../customer/Components/profileNavigation/ProfileNavigation";

const Homepage = () => {
  return (
    <div className="">
      <HomeCarousel images={homeCarouselData} />
      <div className="space-y-10 py-20">
      <img class="responsive" src="https://www.bigbasket.com/media/uploads/flatpages/test-1/Beverages.jpg"  style={{ width:'100%',height:'50%',display: 'flex'}}></img>
         <HomeProductSection data={beverage1} section={"Bevarages"} />
        <HomeProductSection data={snacks1} section={"Snack Store"} />
        <img class="responsive" src="https://www.bigbasket.com/media/uploads/banner_images/ZXPL8214-l1-bev-c-Emperia-Green-Tea-1200x300-25-mar-24.jpg?tr=w-1920,q=80"  style={{ width:'100%',height:'50%',display: 'flex'}}></img>
        <HomeProductSection data={fruits1} section={"Fruits & Vegatables"} />
        <img class="responsive" src="https://www.sotc.in/images/bank-offers/ICICI-Bank-Offer-Banner-1920x545.jpg"  style={{ width:'100%',height:'auto',display:'flex'}}></img>
        </div>
    // </div>
  );
};

export default Homepage;
