package com.company.sales.core;

import com.company.sales.SalesTestContainer;
import com.company.sales.entity.Customer;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.security.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class SampleIntegrationTest {

    @ClassRule
    public static SalesTestContainer cont = SalesTestContainer.Common.INSTANCE;

    private Metadata metadata;
    private Persistence persistence;
    private DataManager dataManager;

    @Before
    public void setUp() throws Exception {
        metadata = cont.metadata();
        persistence = cont.persistence();
        dataManager = AppBeans.get(DataManager.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testLoadUser() {
        try (Transaction tx = persistence.createTransaction()) {
            EntityManager em = persistence.getEntityManager();
            TypedQuery<User> query = em.createQuery(
                    "select u from sec$User u where u.login = :userLogin", User.class);
            query.setParameter("userLogin", "admin");
            List<User> users = query.getResultList();
            tx.commit();
            assertEquals(1, users.size());
        }
    }

    @Test
    public void testCreateCustomer() throws Exception {
        // Create new Customer
        Customer customer = metadata.create(Customer.class);
        customer.setName("Test customer");

        // Save the customer to the database
        dataManager.commit(customer);

        // Load the customer by ID
        Customer loaded = dataManager.load(
                LoadContext.create(Customer.class).setId(customer.getId()).setView(View.LOCAL));

        assertNotNull(loaded);
        assertEquals(customer.getName(), loaded.getName());

        // Remove the customer
        dataManager.remove(loaded);
    }
}