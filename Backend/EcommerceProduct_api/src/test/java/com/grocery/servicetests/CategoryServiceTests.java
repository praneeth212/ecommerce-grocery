package com.grocery.servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.grocery.dto.CategoryDTO;
import com.grocery.exception.ImageServiceException;
import com.grocery.model.Category;
import com.grocery.repository.CategoryRepository;
import com.grocery.service.CategoryService;
import com.grocery.service.ImageStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ImageStorageService imageStorageService;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private MultipartFile file;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        category.setDescription("Test Description");

        file = mock(MultipartFile.class);
    }

    @Test
    void testFindAllCategories() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));

        List<Category> categories = categoryService.findAllCategories();

        assertNotNull(categories);
        assertEquals(1, categories.size());
        assertEquals(category.getName(), categories.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testSaveCategory() throws ImageServiceException {
        when(imageStorageService.storeFile(any(MultipartFile.class))).thenReturn("url");
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category savedCategory = categoryService.saveCategory(category, file);

        assertNotNull(savedCategory);
        assertEquals(category.getName(), savedCategory.getName());
        verify(imageStorageService, times(1)).storeFile(file);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testFindByName() {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));

        Optional<Category> foundCategory = categoryService.findByName("Test Category");

        assertTrue(foundCategory.isPresent());
        assertEquals(category.getName(), foundCategory.get().getName());
        verify(categoryRepository, times(1)).findByName("Test Category");
    }

    @Test
    void testFindById() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        Category foundCategory = categoryService.findById(1L);

        assertNotNull(foundCategory);
        assertEquals(category.getName(), foundCategory.getName());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteCategory() {
        doNothing().when(categoryRepository).deleteById(anyLong());

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllCategoriesWithChildren() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));
        when(categoryRepository.findByParentCategoryId(anyLong())).thenReturn(Arrays.asList());

        List<CategoryDTO> categoriesWithChildren = categoryService.getAllCategoriesWithChildren();

        assertNotNull(categoriesWithChildren);
        assertEquals(1, categoriesWithChildren.size());
        assertEquals(category.getName(), categoriesWithChildren.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }
}
