package hhh20178;

import org.hibernate.jpa.HibernatePersistenceConfiguration;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        var sessionFactory =
                new HibernatePersistenceConfiguration("HHH-20006 Reproducer")
                        .managedClasses(TblTestUser.class, TblTestUserContact.class)
                        // use H2 in-memory database
                        .jdbcUrl("jdbc:h2:mem:db1")
                        .jdbcCredentials("sa", "")
                        .property("hibernate.show_sql", true)
                        .property("hibernate.format_sql", true)
                        // set the Agroal connection pool size
                        .jdbcPoolSize(16)
                        // display SQL in console
                        .showSql(true, true, true)
                        .createEntityManagerFactory();


        // export the inferred database schema
        sessionFactory.getSchemaManager().create(true);

        // persist an entity
        out.println("Insert data to H2 database");
        out.println("------------------------------------------------------------------");
        sessionFactory.inTransaction(session -> {
            var u1 = new TblTestUser("User1");
            var u2 = new TblTestUser("User2");

            session.persist(u1);
            session.persist(u2);

            var u1c1 = new TblTestUserContact(u1, 1, "+00 00000 000-000");
            var u1c2 = new TblTestUserContact(u1, 2, "user1@mail.example.com");
            var u2c1 = new TblTestUserContact(u2, 1, "+11 11111 111-111");
            var u2c2 = new TblTestUserContact(u2, 2, "user2@mail.example.com");

            session.persist(u1c1);
            session.persist(u1c2);
            session.persist(u2c1);
            session.persist(u2c2);
        });

        out.println();
        out.println("Correct result: Just select the entity itself");
        out.println("------------------------------------------------------------------");
        sessionFactory.inSession(session -> {
            var l = session.createQuery("""
                                select qtUser
                                from TblTestUser qtUser
                                    left join fetch qtUser.tblUserContacts
                                 where qtUser.varUserName = ?1""", TblTestUser.class)
                    .setParameter(1, "User1")
                    .getResultList();

            l.forEach(tblUser -> {
                out.println(tblUser + " -> " + tblUser.getIntUserId());
                tblUser.getTblUserContacts().forEach(uc -> {
                    out.println("\t" + uc + " -> " + uc.getIntUserContactId());
                });
            });
        });

        out.println();
        out.println("Wrong result: Return a wrapper containing the selected entity");
        out.println("------------------------------------------------------------------");
        sessionFactory.inSession(session -> {
            var l = session.createQuery("""
                                select new hhh20178.Wrapper(qtUser)
                                from TblTestUser qtUser
                                    left join fetch qtUser.tblUserContacts
                                 where qtUser.varUserName = ?1""", Wrapper.class)
                    .setParameter(1, "User1")
                    .getResultList();

            l.forEach(w -> {
                final TblTestUser tblUser = w.tblUser();
                out.println(tblUser + " -> " + tblUser.getIntUserId());
                tblUser.getTblUserContacts().forEach(uc -> {
                    out.println("\t" + uc + " -> " + uc.getIntUserContactId());
                });
            });
        });
    }
}

