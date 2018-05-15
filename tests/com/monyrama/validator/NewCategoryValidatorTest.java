//package com.monyrama.validator;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import com.monyrama.controller.UniqueID;
//import com.monyrama.entity.PCategory;
//import com.monyrama.ui.resources.Resources;
//import com.monyrama.validator.IEntityValidator;
//import com.monyrama.validator.NewCategoryValidator;
//
//
//public class NewCategoryValidatorTest {
//	private static List<PCategory> categories = new ArrayList<PCategory>();
//	
//	@BeforeClass
//	public static void prepareData() {
//		Resources.initSupportedLocales();
//		Resources.setLocale(new Locale("en"));
//		
//		PCategory category_1 = new PCategory();
//		category_1.setId(UniqueID.get());
//		category_1.setName("Food");
//		category_1.setComment("Food");
//		categories.add(category_1);
//		
//		PCategory category_2 = new PCategory();
//		category_2.setId(UniqueID.get());
//		category_2.setName("Car");
//		category_2.setComment("Different things for car");
//		categories.add(category_2);
//		
//		PCategory category_3 = new PCategory();
//		category_3.setId(UniqueID.get());
//		category_3.setName("Education");
//		categories.add(category_3);
//		
//		PCategory category_4 = new PCategory();
//		category_4.setId(UniqueID.get());
//		category_4.setName("Entertainment");
//		category_4.setComment("Pivo, vodka, devki");
//		categories.add(category_4);
//		
//		PCategory category_5 = new PCategory();
//		category_5.setId(UniqueID.get());
//		category_5.setName("Presents");
//		category_5.setComment("");
//		categories.add(category_5);
//	}
//
//	@Test
//	public void testEmptyName() {
//		PCategory categoryOne = new PCategory();
//		categoryOne.setName(null);
//		IEntityValidator validatorOne = new NewCategoryValidator(categories, categoryOne);
//		assertFalse(validatorOne.validate());
//		assertEquals(validatorOne.message(), Resources.getString("dialogs.warnings.nameempty") + "!");
//		
//		PCategory categoryTwo = new PCategory();
//		categoryTwo.setName("");
//		IEntityValidator validatorTwo = new NewCategoryValidator(categories, categoryTwo);
//		assertFalse(validatorTwo.validate());
//		assertEquals(validatorTwo.message(), Resources.getString("dialogs.warnings.nameempty") + "!");
//		
//		PCategory categoryThree = new PCategory();
//		categoryThree.setName("   ");
//		IEntityValidator validatorTree = new NewCategoryValidator(categories, categoryThree);
//		assertFalse(validatorTree.validate());
//		assertEquals(validatorTree.message(), Resources.getString("dialogs.warnings.nameempty") + "!");
//	}
//
//	@Test
//	public void compareNameWithExistingCategories() {
//		PCategory categoryOne = new PCategory();
//		categoryOne.setName("Education");
//		categoryOne.setComment("Education");
//		IEntityValidator validatorOne = new NewCategoryValidator(categories, categoryOne);
//		assertFalse(validatorOne.validate());
//		assertEquals(validatorOne.message(), Resources.getString("dialogs.warnings.categorynameexist") + "!");
//		
//		PCategory categoryTwo = new PCategory();
//		categoryTwo.setName("Travels");
//		categoryTwo.setComment("Travels around the world");
//		IEntityValidator validatorTwo = new NewCategoryValidator(categories, categoryTwo);
//		assertTrue(validatorTwo.validate());
//		assertNull(validatorTwo.message());
//	}
//}
