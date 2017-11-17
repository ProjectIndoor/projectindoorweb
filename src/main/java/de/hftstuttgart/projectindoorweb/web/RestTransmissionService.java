package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.SimpleResponse;

public interface RestTransmissionService {

    SimpleResponse createSimpleResponse(String content);
}
