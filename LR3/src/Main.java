import java.util.Iterator;

interface Prototype {
    Prototype clone();
}

class Car implements Prototype {
    private String model;
    private double price;

    public Car(String model, double price) {
        this.model = model;
        this.price = price;
    }

    @Override
    public Prototype clone() {
        return new Car(this.model, this.price);
    }

    @Override
    public String toString() {
        return "Car{model='" + model + "', price=" + price + "}";
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

interface Coffee {
    String getDescription();
    double cost();
}

class BasicCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "Basic Coffee";
    }

    @Override
    public double cost() {
        return 5.0;
    }
}

abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;

    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription();
    }

    @Override
    public double cost() {
        return decoratedCoffee.cost();
    }
}

class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Milk";
    }

    @Override
    public double cost() {
        return decoratedCoffee.cost() + 1.5;
    }
}

class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription() + ", Sugar";
    }

    @Override
    public double cost() {
        return decoratedCoffee.cost() + 0.5;
    }
}

interface BookCollection {
    Iterator<Book> createIterator();
}

class Book {
    private String title;

    public Book(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Book{title='" + title + "'}";
    }
}

class Library implements BookCollection {
    private Book[] books;

    public Library() {
        books = new Book[3];
        books[0] = new Book("Book1");
        books[1] = new Book("Book2");
        books[1] = new Book("Book2");
        books[2] = new Book("Book3");
    }
    @Override
    public Iterator<Book> createIterator() {
        return new LibraryIterator();
    }

    private class LibraryIterator implements Iterator<Book> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < books.length && books[currentIndex] != null;
        }

        @Override
        public Book next() {
            return books[currentIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Prototype ---");
        Car originalCar = new Car("Toyota", 30000.0);
        Car clonedCar = (Car) originalCar.clone();
        clonedCar.setPrice(28000.0);
        System.out.println("Original: " + originalCar);
        System.out.println("Cloned: " + clonedCar);

        System.out.println("\n--- Decorator ---");
        Coffee coffee = new BasicCoffee();
        System.out.println("Cost: $" + coffee.cost() + ", Description: " + coffee.getDescription());
        coffee = new MilkDecorator(coffee);
        coffee = new SugarDecorator(coffee);
        System.out.println("Cost: $" + coffee.cost() + ", Description: " + coffee.getDescription());

        System.out.println("\n--- Iterator ---");
        BookCollection library = new Library();
        Iterator<Book> iterator = library.createIterator();
        System.out.println("Books in library:");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}