package com.mobiquity.packaging;

import com.mobiquity.exception.APIException;
import com.mobiquity.packer.Packer;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class PackagingChallangeApplicationTest {

	private static final String FILE_PATH = "src/test/resources/example_input.txt";
	private static final String OVER_MAX_WEIGHT_FILE_PATH = "src/test/resources/over_max_weight_input.txt";
	private static final String OVER_MAX_ITEM_COUNT_FILE_PATH = "src/test/resources/over_max_item_count_input.txt";
	private static final String OVER_MAX_ITEM_COST_FILE_PATH = "src/test/resources/over_max_item_cost_input.txt";

	@Test
	public void pack() throws APIException, IOException {
		Assert.assertEquals(Packer.pack(FILE_PATH), "4\n-\n2,7\n8,9\n");
	}

	@Test
	public void checkPackageOverWeight() throws APIException, IOException {
		Assert.assertEquals(Packer.pack(OVER_MAX_WEIGHT_FILE_PATH), "-\n2,7\n");
	}

	@Test
	public void checkPackageOverItemCount() throws APIException, IOException {
		Assert.assertEquals(Packer.pack(OVER_MAX_ITEM_COUNT_FILE_PATH), "4\n8,13,10,11,12,14,15,16,17,18,19,20,7,2,5\n");
	}

	@Test
	public void checkPackageOverItemCost() throws APIException, IOException {
		Assert.assertEquals(Packer.pack(OVER_MAX_ITEM_COST_FILE_PATH), "4\n-\n");
	}

}
