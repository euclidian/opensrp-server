package org.ei.drishti.listener;

import com.google.gson.Gson;
import org.ei.drishti.dto.form.FormSubmissionDTO;
import org.ei.drishti.event.FormSubmissionEvent;
import org.ei.drishti.form.service.SubmissionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.motechproject.scheduler.domain.MotechEvent;

import java.util.List;

import static java.util.Arrays.asList;
import static org.ei.drishti.util.EasyMap.mapOf;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class FormSubmissionEventListenerTest {
    @Mock
    private SubmissionService formSubmissionService;

    private FormSubmissionEventListener listener;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        listener = new FormSubmissionEventListener(formSubmissionService);
    }

    @Test
    public void shouldDelegateToFormSubmissionService() throws Exception {
        List<FormSubmissionDTO> formSubmissions = asList(new FormSubmissionDTO("anm id 1", "instance id 1", "entity id 1", "form name", null, "0"),
                new FormSubmissionDTO("anm id 2", "instance id 2", "entity id 2", "form name", null, "0"));

        listener.submitForms(new MotechEvent(FormSubmissionEvent.SUBJECT, mapOf("data", (Object) new Gson().toJson(formSubmissions))));

        verify(formSubmissionService).processSubmissions(formSubmissions);
    }
}
