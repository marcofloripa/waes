package com.odaguiri.waes.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.odaguiri.waes.dto.JsonEncodeRequest;
import com.odaguiri.waes.dto.JsonEncodeResponse;
import com.odaguiri.waes.exception.JsonEncodeNotFoundException;
import com.odaguiri.waes.service.JsonEncodeService;

@RestController
@RequestMapping("/v1/diff")
public class JsonEncodeController {

	@Autowired
	private JsonEncodeService jsonEncodeService;
	
	/**
	 * POST  /:id/left : Save the left side
	 * 
	 * @param id
	 * @param json
	 * @return
	 * @throws URISyntaxException
	 */
	@ApiOperation(value = "Create a Json encoded with 'left' content")
    @ApiResponses(value = {
    		@ApiResponse(code = 201, message = "Json encoded created with success"),
    		@ApiResponse(code = 200, message = "Json encoded updated with success")
    })
	@RequestMapping(value = "/{id}/left",
	        method = RequestMethod.POST,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JsonEncodeResponse> left(@PathVariable String id, 
			@Valid @RequestBody JsonEncodeRequest json) throws URISyntaxException {
		JsonEncodeResponse response = jsonEncodeService.save(id, json.getContent(), "left");
		return createPostResponseEntity(response);
	}
	
	/**
	 * POST  /:id/right : Save the right side
	 * 
	 * @param id
	 * @param json
	 * @return
	 * @throws URISyntaxException
	 */
	@ApiOperation(value = "Create a Json encoded with 'right' content")
    @ApiResponses(value = {
    		@ApiResponse(code = 201, message = "Json encoded created with success"),
    		@ApiResponse(code = 200, message = "Json encoded updated with success")
    })
	@RequestMapping(value = "/{id}/right",
	        method = RequestMethod.POST,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JsonEncodeResponse> right(@PathVariable String id, 
			@Valid @RequestBody JsonEncodeRequest json) throws URISyntaxException {
		JsonEncodeResponse response = jsonEncodeService.save(id, json.getContent(), "right");
		return createPostResponseEntity(response);
	}
	
	/**
	 * GET  /:id : Get the result of the comparisons
	 * 
	 * @param id
	 * @return the ResponseEntity with status 200 (OK) and with body the JsonEncodeResponse, or with status 404 (Not Found)
	 * @throws JsonEncodeNotFoundException 
	 * @throws URISyntaxException 
	 */
	@ApiOperation(value = "Process the Json encoded comparison")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Json encoded compare with success"),
            @ApiResponse(code = 404, message = "Json encoded not found")
    })
	@RequestMapping(value = "/{id}",
	        method = RequestMethod.GET,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JsonEncodeResponse> diff(@PathVariable String id) throws JsonEncodeNotFoundException {
		
		Optional<JsonEncodeResponse> op = jsonEncodeService.findById(id);
		if (!op.isPresent()) {
			throw new JsonEncodeNotFoundException("Json with id: " + id + " not found");
		}
		JsonEncodeResponse response = jsonEncodeService.diff(id);
		return createGetResponseEntity(response);
	}

	/**
	 * 
	 * @param jsonEncodeDTO
	 * @return
	 * @throws URISyntaxException
	 */
	private ResponseEntity<JsonEncodeResponse> createPostResponseEntity(
			JsonEncodeResponse jsonEncodeResponse) throws URISyntaxException {
		return jsonEncodeResponse.isCreate() ? 
				ResponseEntity.created(new URI("/v1/diff/" + jsonEncodeResponse.getId())).body(jsonEncodeResponse) : 
					ResponseEntity.ok(jsonEncodeResponse);
	}
	
	/**
	 * 
	 * @param jsonEncodeResponse
	 * @return
	 */
	private ResponseEntity<JsonEncodeResponse> createGetResponseEntity(
			JsonEncodeResponse jsonEncodeResponse) {
		return jsonEncodeResponse.isFound() ? 
				ResponseEntity.ok(jsonEncodeResponse) : 
					ResponseEntity.notFound().build();
	}
}
