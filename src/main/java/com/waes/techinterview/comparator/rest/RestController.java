package com.waes.techinterview.comparator.rest;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.waes.techinterview.comparator.service.comparator.ComparatorService;
import com.waes.techinterview.comparator.service.storage.StorageService;
import com.waes.techinterview.comparator.service.validator.ValidationService;
import com.waes.techinterview.comparator.vo.ComparatorInputSideEnum;
import com.waes.techinterview.comparator.vo.ComparatorInputVO;
import com.waes.techinterview.comparator.vo.ComparatorResultVO;
import com.waes.techinterview.comparator.vo.RequestObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.ws.Response;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Application Rest endpoints.
 * Created by Kunal on 07-11-2018.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/v1/diff/{id}")
public class RestController {

    private static final Logger LOG = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private ValidationService validationService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ComparatorService comparatorService;

    /**
     * Store ethe left side of the argument in comparison
     * @param id unique identifier for an entry. Application will save if ID is new; otherwise update the argument.
     * @param data base 64 based encoded string parameter
     * @return HTTP Response 201: Created if successful
     */
    @RequestMapping(value = "/left", method = RequestMethod.POST, produces = "application/json")
    private ResponseEntity<String> left(@PathVariable Long id, @RequestBody RequestObject data){

        LOG.debug("Setting left side argument for ID (), with value {}", id, data.getData());

        LOG.debug("Validating input parameter for ID {}",id);
        if(!validationService.validateInputData(data.getData())){
            LOG.error("Validation failed for entered input for ID (), with value {}", id, data.getData());
            //TODO throw validation exception

        }
        LOG.debug("Validation completed for input parameter for ID {}",id);

        ComparatorInputVO comparatorInputVO = new ComparatorInputVO(id, data.getData(), ComparatorInputSideEnum.LEFT);
        storageService.store(comparatorInputVO);


        return new ResponseEntity<String>("Data added successfully for id - "+id, HttpStatus.CREATED);
    }

    /**
     * Store the right side of the argument in comparison
     * @param id unique identifier for an entry. Application will save if ID is new; otherwise update the argument.
     * @param data base 64 based encoded string parameter
     * @return HTTP Response 201: Created if successful
     */
    @RequestMapping(value = "/right", method = RequestMethod.POST, produces = "application/json")
    private ResponseEntity<String> right(@PathVariable Long id, @RequestBody RequestObject data){

        LOG.debug("Setting left side argument for ID (ID), with value {VAL}", id, data.getData());

        ComparatorInputVO comparatorInputVO = new ComparatorInputVO(id, data.getData(), ComparatorInputSideEnum.RIGHT);
        storageService.store(comparatorInputVO);

        return new ResponseEntity<String>("Data added successfully for id - "+id,HttpStatus.CREATED);
    }

    /**
     * Result endpoint. It provides a JSON with the comparison's result.
     * @param id
     * @return HTTP Response 200: OK <p>
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    private ResponseEntity<ComparatorResultVO> diff(@PathVariable Long id) {

        ComparatorResultVO comparatorResultVO = comparatorService.compare(id);

        if(comparatorResultVO.getComparatorResultEnum()!=null){
            return new ResponseEntity<ComparatorResultVO>(comparatorResultVO,HttpStatus.OK);
        }else{
            return new ResponseEntity<ComparatorResultVO>(comparatorResultVO,HttpStatus.NOT_FOUND);
        }

    }
}
