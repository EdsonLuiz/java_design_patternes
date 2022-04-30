package com.edson.solid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class srp {
  public static void main(String[] args)  throws Exception {
    Journal j = new Journal();
    j.addEntry("I cried today");
    j.addEntry("I ate a bug");
  
    System.out.println(j);
  }
}

class Journal {
  private final List<String> entries = new ArrayList<>();

  private static int count = 0;

  public void addEntry(String text) {
    entries.add("" + (++count) + ": " + text);
  }

  public void removeEntry(final int index) {
    entries.remove(index);
  }

  public void save(final String fileName) throws FileNotFoundException {
    try(PrintStream out = new PrintStream(fileName)) {
      out.println(toString());
    }
  }

  public void load (String fileName) {}
  public void load (URL url) {}

  @Override
  public String toString() {
    return String.join(System.lineSeparator(), entries);
  }
}

class Persistence {
  public void saveToFile(Journal journal, String fileName, boolean override) throws FileNotFoundException {
    if(override || new File(fileName).exists()) {
      try(PrintStream out = new PrintStream(fileName)) {
        out.println(toString());
      }
    }
  }

  public void load (String fileName) {}
  public void load (URL url) {}
}
