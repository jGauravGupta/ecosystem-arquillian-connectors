/*
 * Copyright (c) 2017-2023 Payara Foundation and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://github.com/payara/Payara/blob/master/LICENSE.txt
 * See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * The Payara Foundation designates this particular file as subject to the "Classpath"
 * exception as provided by the Payara Foundation in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 *
 *
 * This file incorporates work covered by the following copyright and
 * permission notice:
 *
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fish.payara.arquillian.container.payara.remote;

import fish.payara.arquillian.container.payara.CommonPayaraConfiguration;
import fish.payara.arquillian.container.payara.RemoteInstanceConnectionProvider;
import org.jboss.arquillian.container.spi.ConfigurationException;

import java.util.Optional;

import static fish.payara.arquillian.container.payara.clientutils.PayaraClient.ADMINSERVER;
import static org.jboss.arquillian.container.spi.client.deployment.Validate.notNullOrEmpty;

/**
 * Configuration for Remote Payara containers.
 *
 * @author <a href="http://community.jboss.org/people/dan.j.allen">Dan Allen</a>
 * @author <a href="http://community.jboss.org/people/LightGuard">Jason Porter</a>
 * @author Vineet Reynolds
 */
public class PayaraRemoteContainerConfiguration extends CommonPayaraConfiguration implements RemoteInstanceConnectionProvider {
    
    private String httpHost;
    private Integer httpPort;
    
    public Optional<String> getHttpHost() {
        return Optional.ofNullable(httpHost);
    }
    
    /**
     * @param httpHost The hostname or ip address where the remote HTTP end can be reached
     */
    public void setHttpHost(String httpHost) {
        this.httpHost = httpHost;
    }
    
    public Optional<Integer> getHttpPort() {
        return Optional.ofNullable(httpPort);
    }
    
    /**
     * @param httpPort The port where the remote HTTP end can be reached
     */
    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    @Override
    public String getTarget() {
        return ADMINSERVER;
    }

    /**
     * Validates if current configuration is valid, that is if all required properties are set and
     * have correct values
     */
    @Override
    public void validate() throws ConfigurationException {
        if (httpPort != null && (httpPort < 0 || httpPort > 65535)) {
            throw new IllegalArgumentException("The arquillian.xml property httpPort must be between 1 and 65535");
        }
        
        if (httpHost != null && httpHost.isEmpty()) {
            throw new IllegalArgumentException("The arquillian.xml property httpHost must be not null or empty");
        }

        super.validate();
    }
}
