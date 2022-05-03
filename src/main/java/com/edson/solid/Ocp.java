package com.edson.solid;

import java.util.List;
import java.util.stream.Stream;

/**
 * OCP + Specification
 */
public class Ocp {
  public static void main(String[] args) {
    Product apple = new Product("Apple", Size.SMALL, Color.GREEN);
    Product tree = new Product("Tree", Size.LARGE, Color.GREEN);
    Product house = new Product("House", Size.LARGE, Color.BLUE);

    List<Product> products = List.of(apple, tree, house);

    ProductFilter pf = new ProductFilter();
    System.out.println("GREEN Products (OLD): ");
    pf.filterByColor(products, Color.GREEN)
      .forEach(p -> System.out.println(" - " + p.name + " is green"));

    BetterFilter bf = new BetterFilter();
    System.out.println("GREEN Products (NEW): ");
    bf.filter(products, new ColorSpecification(Color.GREEN))
      .forEach(p -> System.out.println(" - " + p.name + " is green"));
  }
}

interface Specification<T> {
  boolean isSatisfied(T item);
}

interface Filter<T> {
  Stream<T> filter(List<T> items, Specification<T> spec);
}

class ColorSpecification implements Specification<Product> {
  private Color color;

  public ColorSpecification(Color color) {
    this.color = color;
  }

  @Override
  public boolean isSatisfied(Product item) {
    return item.color.equals(color);
  }
}

class SizeSpecification implements Specification<Product> {
  private Size size;

  public SizeSpecification(Size size) {
    this.size = size;
  }

  @Override
  public boolean isSatisfied(Product item) {
    return item.size.equals(size);
  }
}

class BetterFilter implements Filter<Product> {

  @Override
  public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
    return items.stream().filter(p -> spec.isSatisfied(p));
  }

}

enum Color {
  RED,
  GREEN,
  BLUE
}

enum Size {
  SMALL, MEDIUM, LARGE, YUGE
}

class Product {
  public String name;
  public Size size;
  public Color color;

  public Product(String name, Size size, Color color) {
    this.name = name;
    this.size = size;
    this.color = color;
  }
}

class ProductFilter {
  public Stream<Product> filterByColor(List<Product> products, Color color) {
    return  products.stream().filter(p -> p.color.equals(color));
  }

  public Stream<Product> filterByColorAndSize(List<Product> products, Size size, Color color) {
    return products.stream().filter(p -> p.size.equals(size) && p.color.equals(color));
  }
}