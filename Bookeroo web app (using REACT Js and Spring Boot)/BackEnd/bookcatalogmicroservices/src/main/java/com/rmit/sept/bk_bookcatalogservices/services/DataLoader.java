package com.rmit.sept.bk_bookcatalogservices.services;

import com.rmit.sept.bk_bookcatalogservices.Repositories.BookRepository;
import com.rmit.sept.bk_bookcatalogservices.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private BookRepository bookRepository;

    @Autowired
    public DataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void run(ApplicationArguments args) {
        Book book1 = new Book();
        book1.setName("Murder on the Orient Express");
        book1.setAuthor("Agatha Christie");
        book1.setCategory("THRILLER");
        book1.setDescription("Just after midnight, a snowdrift stops the Orient Express in its tracks. " +
        "An American tycoon lies dead in his compartment, stabbed a dozen times, his door locked from the inside.\n");
        book1.setIsbn((long) 99798);
        book1.setImage("https://sept-bookcatalog.s3.ap-southeast-2.amazonaws.com/murder-on-the-orient-express.jpg");

        bookRepository.save(book1);


        Book book2 = new Book();
        book2.setName("A Wanted Man");
        book2.setAuthor("Lee Child");
        book2.setCategory("ACTION");
        book2.setDescription("When you're as big and rough as Jack Reacher - and you have a badly set, freshly busted nose, " +
        "it isn't easy to hitch a ride in Nebraska. At last, he's picked up by three strangers - two men and a woman.\n");
        book2.setIsbn((long) 99002);
        book2.setImage("https://sept-bookcatalog.s3.ap-southeast-2.amazonaws.com/lee+child+a+wanted+man.jpg");

        bookRepository.save(book2);


        Book book3 = new Book();
        book3.setName("The Dark Tower: The Gunslinger");
        book3.setAuthor("Stephen King");
        book3.setCategory("FICTION");
        book3.setDescription("A #1 national bestseller, The Gunslinger introduces readers to one of Stephen King's most powerful creations, " +
        "Roland of Gilead: The Last Gunslinger. He is a haunting figure, a loner on a spellbinding journey into good and evil.\n");
        book3.setIsbn((long) 99103);
        book3.setImage("https://sept-bookcatalog.s3.ap-southeast-2.amazonaws.com/dark+tower.jpg");

        bookRepository.save(book3);


        Book book4 = new Book();
        book4.setName("Harry Potter and the Goblet of Fire");
        book4.setAuthor("J.K. Rowling");
        book4.setCategory("FICTION");
        book4.setDescription("When the Quidditch World Cup is disrupted by Voldemort's rampaging supporters and the terrifying Dark Mark " +
        "appears against the night sky, it is obvious to Harry Potter that, far from weakening, Voldemort is getting stronger.\n");
        book4.setIsbn((long) 99223);
        book4.setImage("https://sept-bookcatalog.s3.ap-southeast-2.amazonaws.com/harry+potter+goblet+of+fire.jpg");

        bookRepository.save(book4);


        Book book5 = new Book();
        book5.setName("Harry Potter and the Half-Blood Prince");
        book5.setAuthor("J.K. Rowling");
        book5.setCategory("FICTION");
        book5.setDescription("WDumbledore and Harry Potter learn more about Voldemort's past and his rise to power. " + 
        "Meanwhile, Harry stumbles upon an old potions textbook belonging to a person calling himself the Half-Blood Prince.\n");
        book5.setIsbn((long) 99321);
        book5.setImage("https://sept-bookcatalog.s3.ap-southeast-2.amazonaws.com/half+blood+prince.jpg");

        bookRepository.save(book5);


        Book book6 = new Book();
        book6.setName("The Hobbit");
        book6.setAuthor("J.R.R. Tolkien");
        book6.setCategory("FICTION");
        book6.setDescription("In a hole in the ground there lived a hobbit. Not a nasty, dirty, wet hole, filled with the ends of worms " + 
        "and an oozy smell, nor yet a dry, sandy hole with nothing in it to sit down on or to eat: it was a hobbit-hole, and that means comfort.\n");
        book6.setIsbn((long) 96781);
        book6.setImage("https://sept-bookcatalog.s3.ap-southeast-2.amazonaws.com/hobbit.jpg");

        bookRepository.save(book6);

        Book book7 = new Book();
        book7.setName("The Fault in Our Stars");
        book7.setAuthor("John Green");
        book7.setCategory("FICTION");
        book7.setDescription("Despite the medical miracle that has bought her a few years, Hazel has never been anything but terminal. " +
        "But when a gorgeous plot twist named Augustus Waters suddenly appears at Cancer Kid Support Group, Hazel's story gets completely rewritten.\n");
        book7.setIsbn((long) 97801);
        book7.setImage("https://sept-bookcatalog.s3.ap-southeast-2.amazonaws.com/The_Fault_in_Our_Stars_(Official_Film_Poster).png");

        bookRepository.save(book7);


        Book book8 = new Book();
        book8.setName("The Hitchhiker's Guide to the Galaxy");
        book8.setAuthor("Douglas Adams");
        book8.setCategory("SCI_FI");
        book8.setDescription("Seconds before Earth is demolished to make way for a galactic freeway, Arthur Dent is plucked off the " + 
        "planet by his friend Ford Prefect who, for the last fifteen years, has been posing as an out-of-work actor.\n");
        book8.setIsbn((long) 97166);
        book8.setImage("https://sept-bookcatalog.s3.ap-southeast-2.amazonaws.com/hitchhiker.jpg");

        bookRepository.save(book8);
        log.info("Book data loaded");
    }
}

