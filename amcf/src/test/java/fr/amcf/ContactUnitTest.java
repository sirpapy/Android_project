package fr.amcf;

import org.junit.Test;

import fr.amcf.contactdata.Contact;

import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ContactUnitTest {
    @Test
    public void phoneFormat() throws Exception {
        Contact contact = Contact.builder()
                .setPrimaryPhoneNumber("+33636735708")
                .setEmail("damien@chesneau.fr")
                .setName("Damien Chesneau").build();
        Contact contact2 = Contact.builder()
                .setPrimaryPhoneNumber("0636735708")
                .setEmail("damien@chesneau.fr")
                .setName("Damien Chesneau").build();
        System.out.println("Number c1:" + contact.getPrimaryPhoneNumber());
        System.out.println("Number c2:" + contact2.getPrimaryPhoneNumber());
        assertTrue(contact.isPhoneNumberEquals(contact2.getPrimaryPhoneNumber()));
    }
}