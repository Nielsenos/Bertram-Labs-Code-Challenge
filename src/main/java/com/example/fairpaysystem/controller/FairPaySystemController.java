package com.example.fairpaysystem.controller;

import com.example.fairpaysystem.ConfigLoader;
import com.example.fairpaysystem.ConfigurationService;
import com.example.fairpaysystem.model.BaseConfig;
import com.example.fairpaysystem.model.Employee;
import com.example.fairpaysystem.model.Product;
import com.example.fairpaysystem.response.*;
import com.example.fairpaysystem.service.*;
import com.example.fairpaysystem.service.utils.DirectoryManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST API controller
 */
@RestController
public class FairPaySystemController {

    /**
     * a
     */
    @Value("classpath:empty.json")
    private Resource emptyConfigFile;

    @Value("classpath:onlyproducts.json")
    private Resource onlyProductsConfigFile;

    @Value("classpath:employeesandproducts.json")
    private Resource employeeProductConfigFile;

    /**
     * b
     */
    private ConfigurationService configurationService;
    /**
     * c
     */
    private final EmployeeUtility employeeUtility = new EmployeeUtility();
    /**
     * d
     */
    private final ProductUtility productUtility = new ProductUtility();
    /**
     * e
     */
    private final ValidateInput validateInput = new ValidateInput();

    /**
     * logger
     */
    private Logger log = LoggerFactory.getLogger(FairPaySystemController.class);


    /**
     * Constructor auto wires the configuration
     * @param configurationService
     */
    @Autowired
    public FairPaySystemController(ConfigurationService configurationService)
    {
        this.configurationService = configurationService;
    }





    /**
     *
     * @param fileType
     * @return
     * @throws IOException
     */
    @GetMapping("/changeConfig")
    public ResponseEntity<BaseConfig> bootstrap(String fileType) throws IOException {

        log.info("changeConfig");
        HttpStatus httpStatus = HttpStatus.OK;
        fileType = fileType.toLowerCase();

        try {

            configurationService.getConfigLoader().updateConfig(fileType);
            updateActiveFile(configurationService.getConfiguration());

            return new ResponseEntity<>(configurationService.getConfiguration(), httpStatus);

        }
        catch(Exception e) {

            BaseConfig errorResponse = new BaseConfig();
            errorResponse.setEmployees(null);
            errorResponse.setProducts(null);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("System Error", "-1");
            httpHeaders.add("System Error Message", e.getMessage());
            httpHeaders.add("Request type", "/bootstrap");
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
            return new ResponseEntity<>(errorResponse, httpHeaders, httpStatus);

        }
    }

    @GetMapping("/getConfig")
    public ResponseEntity<BaseConfig> getConfig() {
        int x = 0;
        HttpStatus httpStatus = HttpStatus.OK;

        try {
            return new ResponseEntity<>(configurationService.getConfiguration(), httpStatus);
        }
        catch (Exception e) {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("System Error", "-1");
            httpHeaders.add("System Error Message", "Unexpected Internal Error.");
            httpHeaders.add("Request type", "/getConfig");
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
            return new ResponseEntity<>(httpHeaders, httpStatus);

        }
    }

    /**
     * Who Pays
     * @return
     */
    @GetMapping("/whoPays")
    public ResponseEntity<Employee> getWhoPays() {
        int x = 0;
        HttpStatus httpStatus = HttpStatus.OK;

        try {

            Employee response = employeeUtility.whoPays(configurationService.getConfiguration());
           // configurationService.updateConfigurationFile(configurationService.getConfiguration());
            updateActiveFile(configurationService.getConfiguration());


            return  new ResponseEntity<>(response, httpStatus);

        }
        catch (Exception e) {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("System Error", "-1");
            httpHeaders.add("System Error Message", "Unexpected Internal Error.");
            httpHeaders.add("Request type", "/whoPays");
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
            return new ResponseEntity<>(httpHeaders, httpStatus);

        }


    }

    /**
     * Change favorite drink
     * @param employeeId
     * @param productId
     * @return
     */
    @GetMapping("/changeFavoriteDrink")
    public ResponseEntity<ChangeFavoriteDrinkResponse> changeFavoriteDrink(String employeeId, String productId) {
        int x = 0;

        HttpStatus httpStatus = HttpStatus.OK;
        ChangeFavoriteDrinkResponse response = new ChangeFavoriteDrinkResponse();

        try {

            validateInput.validateChangeFavoriteDrinkInput(configurationService.getConfiguration(), employeeId, productId);

            response.setPreviousFavorite(configurationService.getConfiguration().getProducts().get(configurationService.getConfiguration().getEmployees().get(employeeId).getFavoriteDrinkId()));
            employeeUtility.changeFavoriteDrink(configurationService.getConfiguration(), configurationService.getConfiguration().getProducts().get(productId), configurationService.getConfiguration().getEmployees().get(employeeId));
            response.setEmployee(configurationService.getConfiguration().getEmployees().get(employeeId));
            response.setNewFavorite(configurationService.getConfiguration().getProducts().get(productId));
            //configurationService.updateConfigurationFile(configurationService.getConfiguration());
            updateActiveFile(configurationService.getConfiguration());

            return new ResponseEntity<>(response, httpStatus);


        } catch (Exception e) {

            response.setPreviousFavorite(null);
            response.setEmployee(null);
            response.setNewFavorite(null);
            response.setStatusMessage(e.getMessage());

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("System Error", "-1");
            httpHeaders.add("System Error Message", e.getMessage());
            httpHeaders.add("Request type", "/changeFavoriteDrink");
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
            return new ResponseEntity<>(response, httpHeaders, httpStatus);

        }
    }

    private void updateActiveFile(Object o) throws IOException {
        DirectoryManager directoryManager = configurationService.getConfigLoader().getDirectoryManager();
        BaseConfig baseConfig = configurationService.getConfiguration();
        ObjectMapper objectMapper = new ObjectMapper();
        directoryManager.writeFile(directoryManager.getFullPathFileName(), objectMapper.writeValueAsString(o));
    }

    /**
     * Add product
     * @param productId
     * @param name
     * @param drinkPrice
     * @return
     */
    @GetMapping("/addProduct")
    public ResponseEntity<AddProductResponse> addProduct(String productId, String name, String drinkPrice) {
        int x  = 0;

        HttpStatus httpStatus = HttpStatus.OK;
        AddProductResponse response = new AddProductResponse();

        try {

            validateInput.validateAddProductInput(configurationService.getConfiguration(), productId, name, drinkPrice);

            Product toAdd = new Product(productId, name, Double.parseDouble(drinkPrice));
            productUtility.addNewProduct(configurationService.getConfiguration(), toAdd);
            response.setProductAdded(toAdd);
            response.setProductList(configurationService.getConfiguration().getProducts());
            //configurationService.updateConfigurationFile(configurationService.getConfiguration());
            updateActiveFile(configurationService.getConfiguration());

            return  new ResponseEntity<>(response, httpStatus);

        }
        catch (Exception e) {

            response.setProductList(null);
            response.setProductAdded(null);
            response.setStatusMessage(e.getMessage());

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("System Error", "-1");
            httpHeaders.add("System Error Message", e.getMessage());
            httpHeaders.add("Request type", "/addEmployee");
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
            return new ResponseEntity<>(response, httpHeaders, httpStatus);

        }



    }

    /**
     * Remove product
     * @param productId
     * @return
     */
    @GetMapping("/removeProduct")
    public ResponseEntity<RemoveProductResponse> removeProduct (String productId) {

        HttpStatus httpStatus = HttpStatus.OK;
        RemoveProductResponse response = new RemoveProductResponse();

        try {

                validateInput.validateRemoveProductInput(configurationService.getConfiguration(), productId);

                response.setRemovedProduct(configurationService.getConfiguration().getProducts().get(productId));
                productUtility.removeProduct(configurationService.getConfiguration(), configurationService.getConfiguration().getProducts().get(productId));
                response.setProductList(configurationService.getConfiguration().getProducts());
                //configurationService.updateConfigurationFile(configurationService.getConfiguration());
                updateActiveFile(configurationService.getConfiguration());

                return  new ResponseEntity<>(response, httpStatus);

        }
        catch (Exception e) {

            response.setProductList(null);
            response.setRemovedProduct(null);
            response.setStatusMessage(e.getMessage());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("System Error", "-1");
            httpHeaders.add("System Error Message", e.getMessage());
            httpHeaders.add("Request type", "/removeProduct");
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
            return new ResponseEntity<>(response, httpHeaders, httpStatus);

        }

    }

    /**
     * Add employee
     * @param employeeId
     * @param name
     * @param favoriteDrinkId
     * @return
     */
    @GetMapping("/addEmployee")
    public ResponseEntity<AddEmployeeResponse> addEmployee (String employeeId, String name, String favoriteDrinkId) {
        int x = 0;

        HttpStatus httpStatus = HttpStatus.OK;
        AddEmployeeResponse response = new AddEmployeeResponse();


        try {

            validateInput.validateAddEmployeeInput(configurationService.getConfiguration(), employeeId, name, favoriteDrinkId);

            Employee toAdd = new Employee(employeeId, name, favoriteDrinkId, 0.0);
            employeeUtility.addEmployee(configurationService.getConfiguration(), toAdd);
            response.setAddedEmployee(toAdd);
            response.setEmployeeList(configurationService.getConfiguration().getEmployees());
            //configurationService.updateConfigurationFile(configurationService.getConfiguration());
            updateActiveFile(configurationService.getConfiguration());

            return  new ResponseEntity<>(response, httpStatus);

        }
        catch (Exception e) {

            response.setEmployeeList(null);
            response.setAddedEmployee(null);
            response.setStatusMessage(e.getMessage());

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("System Error", "-1");
            httpHeaders.add("System Error Message", e.getMessage());
            httpHeaders.add("Request type", "/addEmployee");
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
            return new ResponseEntity<>(response, httpHeaders, httpStatus);

        }
    }

    /**
     * Remove employee
     * @param employeeId
     * @return
     */
    @GetMapping("/removeEmployee")
    public ResponseEntity<RemoveEmployeeResponse> removeEmployee (String employeeId) {

        HttpStatus httpStatus = HttpStatus.OK;
        RemoveEmployeeResponse response = new RemoveEmployeeResponse();

        try {

            validateInput.validateRemoveEmployeeInput(configurationService.getConfiguration(), employeeId);

            response.setEmployeeRemoved(configurationService.getConfiguration().getEmployees().get(employeeId));
            employeeUtility.removeEmployee(configurationService.getConfiguration(), configurationService.getConfiguration().getEmployees().get(employeeId));
            response.setEmployeeList(configurationService.getConfiguration().getEmployees());

            updateActiveFile(configurationService.getConfiguration());

            return new ResponseEntity<>(response,httpStatus);


        }
        catch (Exception e) {

            response.setEmployeeList(null);
            response.setEmployeeRemoved(null);
            response.setStatusMessage(e.getMessage());

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("System Error", "-1");
            httpHeaders.add("System Error Message", e.getMessage());
            httpHeaders.add("Request type", "/removeEmployee");
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
            return new ResponseEntity<>(response, httpHeaders, httpStatus);

        }
    }

    /**
     * Get product information
     * @return
     */
    @GetMapping("/getProductInformation")
    public ResponseEntity<ProductInformationResponse> getAllProductInfo() {
        int x = 0;

        HttpStatus httpStatus = HttpStatus.OK;
        ProductInformationResponse response = new ProductInformationResponse();

        try {

            response.setProductList(configurationService.getConfiguration().getProducts());
            return  new ResponseEntity<>(response, httpStatus);
        }
        catch (Exception e) {

            response.setProductList(null);
            response.setStatusMessage("Unexpected internal error");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("System Error", "-1");
            httpHeaders.add("System Error Message", "Unexpected Internal Error.");
            httpHeaders.add("Request type", "/getAllProductInformation");
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
            return new ResponseEntity<>(response, httpHeaders, httpStatus);

        }
    }

    /**
     * Get employee information
     * @return
     */
    @GetMapping("/getEmployeeInformation")
    public ResponseEntity<EmployeeInformationResponse> getAllEmployeeInfo() {
        int x = 0;

        HttpStatus httpStatus = HttpStatus.OK;
        EmployeeInformationResponse response = new EmployeeInformationResponse();

        try {

            response.setEmployeeList(configurationService.getConfiguration().getEmployees());
            return  new ResponseEntity<>(response, httpStatus);

        }
        catch (Exception e) {

            response.setEmployeeList(null);
            response.setStatusMessage("Unexpected Internal Error");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("System Error", "-1");
            httpHeaders.add("System Error Message", "Unexpected Internal Error.");
            httpHeaders.add("Request type", "/getEmployeeInformation");
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
            return new ResponseEntity<>(response, httpHeaders, httpStatus);

        }
    }

}
