import AliceCarousel from "react-alice-carousel";
import HomeCategoryCard from "./HomeCategoryCard";

const HomeCategorySection = ({ data }) => {
  const responsive = {
    0: {
      items: 1,
      itemsFit: "contain",
    },
    568: {
      items: 2,
      itemsFit: "contain",
    },
    1024: {
      items: 3,
      itemsFit: "contain",
    },
    1280: {
      items: 4,
      itemsFit: "contain",
    }
  };

  const items = data?.map((item) => (
    <div
      key={item.id}
      className="category-card p-4 hover transition-transform transform hover:scale-105">
      <HomeCategoryCard category={item} />
    </div>
  ));
  

  return (
    <div className="relative px-4 sm:px-6 lg:px-8 ">
      <div className="relative border p-3">
        <AliceCarousel
          disableButtonsControls
          disableDotsControls
          mouseTracking
          items={items}
          responsive={responsive}
          animationType="fadeout"
          animationDuration={2000}
        />
      </div>
    </div>
  );
};

export default HomeCategorySection;
