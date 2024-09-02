export const filters = [
    {
      id: "weight",
      name: "Weight",
      options: [
        {value: "100 Gms", label: "100 Gms"},
        { value: "250 Gms", label: "250 Gms" },
        { value: "500 Gms", label: "500 Gms" },
        { value: "1 Kg", label: "1 Kg" },
        { value:"3-Pack", label:"3-Pack"}
      ],
    }
  ];
  
  export const singleFilter = [
    {
      id: "discount",
      name: "Discount Range",
      options: [
        { value: "0", label: "0% and Above"},
        { value: "5", label: "5% And Above" },
        { value: "10", label: "10% And Above" },
        { value: "20", label: "20% And Above" }
      ],
    },
    {
      id: "stock",
      name: "Availability",
      options: [
        { value: "in_stock", label: "In Stock" },
        { value: "out_of_stock", label: "Out Of Stock" },
      ],
    },
  ]
  