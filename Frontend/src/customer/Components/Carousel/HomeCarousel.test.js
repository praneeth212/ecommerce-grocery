import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import HomeCarousel from './HomeCarousel.jsx';
import { homeCarouselData } from './HomeCaroselData'; 

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: jest.fn(),
}));

describe('HomeCarousel Component', () => {
  const navigate = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
    require('react-router-dom').useNavigate.mockReturnValue(navigate);
  });

  it('renders the carousel with images', () => {
    render(
      <Router>
        <HomeCarousel />
      </Router>
    );

    homeCarouselData.forEach((item) => {
      expect(screen.getByAltText('')).toBeInTheDocument();
      expect(screen.getByRole('presentation')).toHaveAttribute('src', item.image);
    });
  });

  it('navigates to the correct path on image click', () => {
    render(
      <Router>
        <HomeCarousel />
      </Router>
    );

    const firstImage = screen.getByRole('presentation', { name: '' });
    fireEvent.click(firstImage);
    expect(navigate).toHaveBeenCalledWith(homeCarouselData[0].path);
  });
});
