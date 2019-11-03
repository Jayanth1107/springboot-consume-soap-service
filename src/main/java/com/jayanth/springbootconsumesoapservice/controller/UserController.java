package com.jayanth.springbootconsumesoapservice.controller;

import java.io.IOException;

import javax.xml.bind.JAXBElement;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;

import com.jayanth.springbootconsumesoapservice.wsdl.GetUserRequest;
import com.jayanth.springbootconsumesoapservice.wsdl.GetUserResponse;
import com.jayanth.springbootconsumesoapservice.wsdl.ObjectFactory;
import com.jayanth.springbootconsumesoapservice.wsdl.User;

@RestController
public class UserController {

	@Autowired
	@Qualifier("webServiceTemplate")
	private WebServiceTemplate webServiceTemplate;

	@Value("#{'${service.soap.action}'}")
	private String serviceSoapAction;

	@GetMapping("/userDetails/{name}")
	public User getUserDetails(@PathVariable("name") String user) {
		final GetUserRequest userRequest = createUserRequest(user);
		
		final GetUserResponse userResponse = (GetUserResponse) sendAndRecieve(userRequest);
		
		System.out.println(userResponse.getUser());
		return userResponse.getUser();
	}

	private Object sendAndRecieve(GetUserRequest userRequest) {
		return webServiceTemplate.marshalSendAndReceive(userRequest, new WebServiceMessageCallback() {
			public void doWithMessage(WebServiceMessage message) throws IOException, TransformerException {
				SoapMessage soapMessage = (SoapMessage) message;
				soapMessage.setSoapAction(serviceSoapAction);
				// SoapHeader header = soapMessage.getSoapHeader();
				// final StringWriter out = new StringWriter();
				GetUserResponse response = (GetUserResponse) webServiceTemplate.marshalSendAndReceive(userRequest);
				System.out.println(response.getUser().getEmpId());
				/*
				 * getMarshaller().marshal(header, //getHeader(serviceUserId,
				 * serviceUserPassword), new StreamResult(out)); Transformer transformer =
				 * TransformerFactory.newInstance().newTransformer(); transformer.transform(new
				 * StringSource(out.toString()), soapheader.getResult());
				 */
			}
		});
	}

	/*
	 * private Object getHeader(final String userId, final String password) { final
	 * ObjectFactory headerObjectFactory = new ObjectFactory(); final
	 * ApplicationCredentials applicationCredentials = new ApplicationCredentials();
	 * applicationCredentials.setUserId(userId);
	 * applicationCredentials.setPassword(password); final
	 * JAXBElement<ApplicationCredentials> header = headerObjectFactory
	 * .createApplicationCredentials(applicationCredentials); return header; }
	 */

	private GetUserRequest createUserRequest(String user) {
		ObjectFactory objectFactory = new ObjectFactory();
		GetUserRequest userRequest = objectFactory.createGetUserRequest();
		userRequest.setName(user);
		return userRequest;
	}

}
