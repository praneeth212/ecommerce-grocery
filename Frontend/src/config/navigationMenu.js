/* istanbul ignore file */ 
export const navigation = {
    categories: [
      {
        id: 'women',
        name: 'Bevarages',
        featured: [
          {
            name: 'New Arrivals',
            href: '/',
            imageSrc: 'https://www.bigbasket.com/media/uploads/p/m/40172375_8-tata-added-mineral-water-copper-plus-for-immunity-active-lifestyle.jpg?tr=w-1920,q=80',
            imageAlt: 'arrive',
          },
          {
            name: 'Top Rated',
            href: '/',
            imageSrc: 'https://www.bigbasket.com/media/uploads/p/m/40046727_3-fogg-scent-make-my-day-for-women.jpg?tr=w-1080,q=80',
            imageAlt: 'Top',
          },
        ],
        sections: [
          {
            id: 'clothing',
            name: 'Health & Nutrition',
            items: [
              { name: 'tea &coffee', id:"top", href: `{women/clothing/tops}` },
              { name: 'Juices', id:"women_dress", href: '#' },
              { name: 'Water', id: 'women_jeans' },
              { name: 'Soft Drinks', id: 'lengha_choli' },
            ],
          },
          {
            id: 'accessories',
            name: 'Fruits and Vegatables',
            items: [
              { name: 'Fresh Vegtables', id: 'watch' },
              { name: 'Fresh Fruits', id: 'wallet' },
              { name: 'Cuts & Exotics', id: 'bag' },
              { name: 'Herbs & Seasoning', id: 'sunglasse' },
            ],
          },
          {
            id: 'brands',
            name: 'Beauty and Hygine',
            items: [
              { name: 'Scentsational Perfumes', id: '#' },
              { name: 'Shaving Care', id: '#' },
              { name: 'Moisturisers & Serums', id: '#' },
              { name: 'Makeup Show Stoppers', id: '#' },
          
            ],
          },
        ],
      },
      {
        id: 'men',
        name: 'Beauty and hygine',
        featured: [
          {
            name: 'New Arrivals',
            id: '#',
            imageSrc: 'https://dressings-sauces.org/wp-content/uploads/2018/10/AdobeStock_108154845-1024x683.jpeg',
            imageAlt: 'Drawstring top with elastic loop closure and textured interior padding.',
          },
          {
            name: 'Most Purchased',
            id: '#',
            imageSrc: 'https://www.bigbasket.com/media/uploads/p/m/10000097_19-fresho-coriander-leaves.jpg?tr=w-1920,q=80',
            imageAlt:
              'Three shirts in gray, white, and blue arranged on table with same line drawing of hands and shapes overlapping on front of shirt.',
          },
        ],
        sections: [
          {
            id: 'clothing',
            name: 'Scentsational Perfumes',
            items: [
              { name: 'Mens Kurtas', id: 'mens_kurta' },
              { name: 'Shirt', id: 'shirt' },
              { name: 'Men Jeans', id: 'men_jeans' },
              { name: 'Sweaters', id: '#' },
              { name: 'T-Shirts', id: 't-shirt' },
              { name: 'Jackets', id: '#' },
              { name: 'Activewear', id: '#' },
              
            ],
          },
          {
            id: 'accessories',
            name: 'Shaving Care',
            items: [
              { name: 'Watches', id: '#' },
              { name: 'Wallets', id: '#' },
              { name: 'Bags', id: '#' },
              { name: 'Sunglasses', id: '#' },
              { name: 'Hats', id: '#' },
              { name: 'Belts', id: '#' },
            ],
          },
          {
            id: 'brands',
            name: 'Moisturisers & Serums',
            items: [
              { name: 'Re-Arranged', id: '#' },
              { name: 'Counterfeit', id: '#' },
              { name: 'Full Nelson', id: '#' },
              { name: 'My Way', id: '#' },
            ],
          },
        ],
      },
    ],
    pages: [
      { name: 'Snack Cookie', id: '/' },
      { name: 'Deals %', id: '/' },
    ],
  }