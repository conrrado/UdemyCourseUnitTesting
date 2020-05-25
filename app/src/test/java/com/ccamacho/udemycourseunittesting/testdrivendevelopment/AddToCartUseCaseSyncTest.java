package com.ccamacho.udemycourseunittesting.testdrivendevelopment;

import com.ccamacho.udemycourseunittesting.testDoublesFundamentals.networking.NetworkErrorException;
import com.ccamacho.udemycourseunittesting.testdrivendevelopment.AddToCartUseCaseSync.UseCaseResult;
import com.ccamacho.udemycourseunittesting.testdrivendevelopment.networking.AddToCartHttpEndpointSync;
import com.ccamacho.udemycourseunittesting.testdrivendevelopment.networking.CartItemScheme;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.ccamacho.udemycourseunittesting.testdrivendevelopment.networking.AddToCartHttpEndpointSync.EndpointResult.AUTH_ERROR;
import static com.ccamacho.udemycourseunittesting.testdrivendevelopment.networking.AddToCartHttpEndpointSync.EndpointResult.GENERAL_ERROR;
import static com.ccamacho.udemycourseunittesting.testdrivendevelopment.networking.AddToCartHttpEndpointSync.EndpointResult.SUCCESS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddToCartUseCaseSyncTest {

    private static final String OFFER_ID = "offer_id";
    private static final int AMOUNT = 4;

    @Mock
    AddToCartHttpEndpointSync addToCartHttpEndpointSyncMock;

    AddToCartUseCaseSync addToCartTest;

    @Before
    public void setup() throws Exception {
        addToCartTest = new AddToCartUseCaseSync(addToCartHttpEndpointSyncMock);
        success();
    }

    private void success() throws NetworkErrorException {
        when(addToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class))).thenReturn(SUCCESS);
    }

    // correct parameters passed to the endpoint

    @Test
    public void addToCartSync_correctParametersPassedToEndpoint() throws Exception {
        ArgumentCaptor<CartItemScheme> argumentCaptor = ArgumentCaptor.forClass(CartItemScheme.class);
        addToCartTest.addToCartSync(OFFER_ID, AMOUNT);
        verify(addToCartHttpEndpointSyncMock).addToCartSync(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getOfferId(), is(OFFER_ID));
        assertThat(argumentCaptor.getValue().getAmount(), is(AMOUNT));
    }

    // endpoint success - success returned

    @Test
    public void addTodCartSync_success_successReturned() throws Exception {
        UseCaseResult result = addToCartTest.addToCartSync(OFFER_ID, AMOUNT);
        assertThat(result, is(UseCaseResult.SUCCESS));
    }

    // endpoint auth error - failure returned

    @Test
    public void addTodCartSync_authError_failureReturned() throws Exception {
        authError();
        UseCaseResult result = addToCartTest.addToCartSync(OFFER_ID, AMOUNT);
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    private void authError() throws NetworkErrorException {
        when(addToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class)))
                .thenReturn(AUTH_ERROR);
    }

    // endpoint general error - failure returned

    @Test
    public void addTodCartSync_generalError_failureReturned() throws Exception {
        generalError();
        UseCaseResult result = addToCartTest.addToCartSync(OFFER_ID, AMOUNT);
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    private void generalError() throws NetworkErrorException {
        when(addToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class)))
                .thenReturn(GENERAL_ERROR);
    }

    // network exception - network error returned

    @Test
    public void addTodCartSync_networkError_failureReturned() throws Exception {
        networkError();
        UseCaseResult result = addToCartTest.addToCartSync(OFFER_ID, AMOUNT);
        assertThat(result, is(UseCaseResult.NETWORK_ERROR));
    }

    private void networkError() throws NetworkErrorException {
        when(addToCartHttpEndpointSyncMock.addToCartSync(any(CartItemScheme.class)))
                .thenThrow(new NetworkErrorException());
    }
}
