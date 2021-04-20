//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//import wpProject.model.InvoiceTransaction;
//import wpProject.model.Procurement;
//import wpProject.model.User;
//import wpProject.service.AccountService;
//import wpProject.service.ProcurementService;
//import wpProject.service.TransactionService;
//import wpProject.service.UserService;
//
//import java.util.List;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//import static org.mockito.Mockito.when;
//
///**
// * @author nilufer
// * @project onlineAccounting
// */
//
//@ActiveProfiles("test")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//public class AccountingApplicationTests {
//    MockMvc mockMvc;
//
//    UserService userService;
//
//    AccountService accountService;
//
//    ProcurementService procurementService;
//
//    TransactionService transactionService;
//    private Object Role;
//
//    public AccountingApplicationTests(MockMvc mockMvc, UserService userService, AccountService accountService, ProcurementService procurementService, TransactionService transactionService) {
//        this.mockMvc = mockMvc;
//        this.userService = userService;
//        this.accountService = accountService;
//        this.procurementService = procurementService;
//        this.transactionService = transactionService;
//    }
//
//    public static Procurement procurements;
//    private static boolean dataInitialized = false;
//
//    @BeforeEach
//    public void setup(WebApplicationContext wac) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//        initData();
//    }
//
//    private void initData() {
//        if (!dataInitialized) {
//            List<InvoiceTransaction> transaction = transactionService.findInvoiceTransactionList("user");
//            transactionService.create("c2", "c2");
//
//            User user1 = (User) userService.save("m1").get(0);
//            userService.save("m2");
//
//            String user = "user";
//            String admin = "admin";
//
//            userService.register(user, user, user, user, user, Role);
//            userService.register(admin, admin, admin, admin, admin, Role);
//            dataInitialized = true;
//        }
//    }
//
//    @Test
//    public void contextLoads() {
//    }
//
//    @Test
//    public void testGetTransaction() throws Exception {
//        MockHttpServletRequestBuilder transactionRequest = MockMvcRequestBuilders.get("/betweenAccounts");
//        this.mockMvc.perform(transactionRequest)
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.model().attributeExists("transfer"))
//                .andExpect(MockMvcResultMatchers.model().attribute("bodyContent", "transfer"))
//                .andExpect(MockMvcResultMatchers.view().name("master-template"));
//
//    }
//
//    @Test
//    public void testDeleteProcurement() throws Exception {
//        //
//        // Given
//        //
//        User user = new User("John", "Test", "test@hotmail.com");
//        when(userService.save(user).thenReturn(47891));
//
//        //
//        // When
//        //
//        String name = String.valueOf(userService.saveUser(user));
//
//        //
//        // Then
//        //
//        assertThat(name, is("John Test"));
//    }
//    }
////        Object procurement= this.procurementService.createProcurements("test", procurements.getId(), procurements.getId()).get(0);
////        MockHttpServletRequestBuilder procurementDeleteRequest = MockMvcRequestBuilders
////                .delete("/procurement/delete/" + procurement.toString());
////
////        this.mockMvc.perform(procurementDeleteRequest)
////                .andDo(MockMvcResultHandlers.print())
////                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
////                .andExpect(MockMvcResultMatchers.redirectedUrl("/procurement"));
////    }
//
//}
//
