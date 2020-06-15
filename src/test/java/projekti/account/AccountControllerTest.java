package projekti.account;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void init() {
        accountService.saveAccount("Asd Qwe", "asdqwe", "qwerty123", "aqwe");
    }

    @Test
    public void registerPageStatusOk() throws Exception {
        mockMvc.perform(
                get("/register"))
                .andExpect(status().isOk());
    }

    @Test
    public void validAccountCanBeRegistered() throws Exception {
        mockMvc.perform(
                post("/register")
                .param("name","Newt Yousah")
                .param("username", "Neyous")
                .param("password", "qwerty123")
                .param("urlString", "Neyous"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        assertNotNull(accountService.getAccountByUrlString("Neyous"));
    }

    @Test
    public void accountPageExists() throws Exception {
        MvcResult res = mockMvc.perform(
                get("/users/aqwe"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account"))
                .andReturn();

        Account account = (Account) res.getModelAndView().getModel().get("account");

        assertEquals("asdqwe", account.getUsername());
    }

    @After
    public void tearDown() {
        accountRepository.deleteAll();
    }
}
