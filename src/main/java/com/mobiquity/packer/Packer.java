package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.packaging.PackagingChallangeApplicationImpl;

import java.io.IOException;

public class Packer {

  private Packer() {
  }

  public static String pack(String filePath) throws APIException, IOException {

    PackagingChallangeApplicationImpl packagingChallangeApplication = new PackagingChallangeApplicationImpl();
    return packagingChallangeApplication.processPackageFile(filePath);
  }

}
