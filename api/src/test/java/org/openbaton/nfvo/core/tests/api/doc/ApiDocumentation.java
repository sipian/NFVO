/*
 * Copyright (c) 2015 Fraunhofer FOKUS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.openbaton.nfvo.core.tests.api.doc;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openbaton.catalogue.mano.common.*;
import org.openbaton.catalogue.mano.common.faultmanagement.FaultManagementPolicy;
import org.openbaton.catalogue.mano.common.faultmanagement.VRFaultManagementPolicy;
import org.openbaton.catalogue.mano.descriptor.*;
import org.openbaton.catalogue.mano.record.*;
import org.openbaton.catalogue.nfvo.*;
import org.openbaton.exceptions.BadFormatException;
import org.openbaton.exceptions.QuotaExceededException;
import org.openbaton.nfvo.api.*;
import org.openbaton.nfvo.core.interfaces.*;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



/**
 * Created by tbr on 23.09.15.
 */

public class ApiDocumentation {


    private RestDocumentationResultHandler document;

    @Rule
    public final RestDocumentation restDocumentation = new RestDocumentation("./build/api-documentation/generated-snippets");

    private MockMvc mockMvc;


    @Mock
    private NetworkServiceDescriptorManagement networkServiceDescriptorManagement;

    @InjectMocks
    private RestNetworkServiceDescriptor restNetworkServiceDescriptor;


    @Mock
    private VirtualNetworkFunctionManagement vnfdManagement;

    @InjectMocks
    private RestVirtualNetworkFunctionDescriptor restVirtualNetworkFunctionDescriptor;


    @Mock
    private NetworkServiceRecordManagement networkServiceRecordManagement;

    @InjectMocks
    private RestNetworkServiceRecord restNetworkServiceRecord;



    @Mock
    private VimManagement vimManagement;

    @InjectMocks
    private RestVimInstances restVimInstances;



    @Mock
    private VNFPackageManagement vnfPackageManagement;

    @InjectMocks
    private RestVNFPackage restVNFPackage;




    private Gson gson = new Gson();


    private Set<NetworkServiceDescriptor> nsdSet = new HashSet<NetworkServiceDescriptor>();
    private Set<NetworkServiceDescriptor> nsdSetReturn = new HashSet<NetworkServiceDescriptor>();
    private NetworkServiceDescriptor iperfNsd1 = new NetworkServiceDescriptor();
    private NetworkServiceDescriptor iperfNsd1Return = new NetworkServiceDescriptor();
    private Set<VirtualNetworkFunctionDescriptor> iperfVnfdSet = new HashSet<VirtualNetworkFunctionDescriptor>();
    private Set<VirtualNetworkFunctionDescriptor> iperfVnfdSetReturn = new HashSet<VirtualNetworkFunctionDescriptor>();
    private Set<VirtualLinkDescriptor> iperfVldSet = new HashSet<VirtualLinkDescriptor>();
    private Set<VNFDependency> iperfVnfdepSet = new HashSet<VNFDependency>();
    private VNFDependency iperfDep = new VNFDependency();
    private VirtualNetworkFunctionDescriptor iperfVnfdServer = new VirtualNetworkFunctionDescriptor();
    private VirtualNetworkFunctionDescriptor iperfVnfdClient = new VirtualNetworkFunctionDescriptor();
    private VirtualNetworkFunctionDescriptor iperfVnfdServerReturn = new VirtualNetworkFunctionDescriptor();
    private VirtualNetworkFunctionDescriptor iperfVnfdClientReturn = new VirtualNetworkFunctionDescriptor();
    private Configuration configuration = new Configuration();
    private Configuration configurationReturn = new Configuration();
    private Set<ConfigurationParameter> confParameters = new HashSet<ConfigurationParameter>();
    private Set<ConfigurationParameter> confParametersReturn = new HashSet<ConfigurationParameter>();
    private ConfigurationParameter confPar = new ConfigurationParameter();
    private ConfigurationParameter confParReturn = new ConfigurationParameter();
    private Set<VirtualDeploymentUnit> vduServerSet = new HashSet<VirtualDeploymentUnit>();
    private Set<VirtualDeploymentUnit> vduServerSetReturn = new HashSet<VirtualDeploymentUnit>();
    private VirtualDeploymentUnit vduServer = new VirtualDeploymentUnit();
    private VirtualDeploymentUnit vduServerReturn = new VirtualDeploymentUnit();
    private Set<String> imageSet = new HashSet<String>();
    private Set<String> imageSetReturn = new HashSet<String>();
    private Set<VNFComponent> vnfcSet = new HashSet<VNFComponent>();
    private Set<VNFComponent> vnfcSetReturn = new HashSet<VNFComponent>();
    private Set<VNFComponent> vnfcSetReturn2 = new HashSet<VNFComponent>();
    private VNFComponent vnfc = new VNFComponent();
    private VNFComponent vnfcReturn1 = new VNFComponent();
    private VNFComponent vnfcReturn2 = new VNFComponent();
    private VNFComponent vnfcReturn3 = new VNFComponent();
    private Set<VNFDConnectionPoint> vnfdConnectionPointSet = new HashSet<VNFDConnectionPoint>();
    private Set<VNFDConnectionPoint> vnfdConnectionPointSetReturn1 = new HashSet<VNFDConnectionPoint>();
    private Set<VNFDConnectionPoint> vnfdConnectionPointSetReturn2 = new HashSet<VNFDConnectionPoint>();
    private Set<VNFDConnectionPoint> vnfdConnectionPointSetReturn3 = new HashSet<VNFDConnectionPoint>();
    private VNFDConnectionPoint vnfdConnectionPoint = new VNFDConnectionPoint();
    private VNFDConnectionPoint vnfdConnectionPointReturn1 = new VNFDConnectionPoint();
    private VNFDConnectionPoint vnfdConnectionPointReturn2 = new VNFDConnectionPoint();
    private VNFDConnectionPoint vnfdConnectionPointReturn3 = new VNFDConnectionPoint();
    private Set<InternalVirtualLink> vlSet = new HashSet<InternalVirtualLink>();
    private Set<InternalVirtualLink> vlSetReturn = new HashSet<InternalVirtualLink>();
    private InternalVirtualLink vlReturn = new InternalVirtualLink();
    private InternalVirtualLink vl = new InternalVirtualLink();
    private Set<LifecycleEvent> leServerSet = new HashSet<LifecycleEvent>();
    private Set<LifecycleEvent> leServerSetReturn = new HashSet<LifecycleEvent>();
    private Set<LifecycleEvent> leClientSet = new HashSet<LifecycleEvent>();
    private Set<LifecycleEvent> leClientSetReturn = new HashSet<LifecycleEvent>();
    private LifecycleEvent inst = new LifecycleEvent();
    private LifecycleEvent instReturn = new LifecycleEvent();
    private LifecycleEvent startServer = new LifecycleEvent();
    private LifecycleEvent startServerReturn = new LifecycleEvent();
    private LifecycleEvent startClient = new LifecycleEvent();
    private LifecycleEvent startClientReturn = new LifecycleEvent();
    private List<String> instScripts = new LinkedList<String>();
    private List<String> startServerScripts = new LinkedList<String>();
    private List<String> startClientScripts = new LinkedList<String>();
    private Set<VNFDeploymentFlavour> deploymentFlavSet = new HashSet<VNFDeploymentFlavour>();
    private Set<VNFDeploymentFlavour> deploymentFlavSetReturn = new HashSet<VNFDeploymentFlavour>();
    private VNFDeploymentFlavour deplFlav = new VNFDeploymentFlavour();
    private VNFDeploymentFlavour deplFlavReturn = new VNFDeploymentFlavour();
    private Set<VirtualDeploymentUnit> vduClientSet = new HashSet<VirtualDeploymentUnit>();
    private Set<VirtualDeploymentUnit> vduClientSetReturn = new HashSet<VirtualDeploymentUnit>();
    private VirtualDeploymentUnit vduClient = new VirtualDeploymentUnit();
    private VirtualDeploymentUnit vduClientReturn = new VirtualDeploymentUnit();
    private Set<String> depParamSet = new HashSet<String>();
    private Set<VirtualNetworkFunctionDescriptor> vnfdSet = new HashSet<VirtualNetworkFunctionDescriptor>();
    private VNFPackage vnfPackage0 = new VNFPackage();
    private VNFPackage vnfPackage1 = new VNFPackage();
    private VNFPackage vnfPackage1Return = new VNFPackage();
    private Set<VNFPackage> vnfPackageSet = new HashSet<VNFPackage>();
    private NFVImage image = new NFVImage();
    private Set<Script> scripts = new HashSet<Script>();
    private Script install = new Script();
    private Script start = new Script();
    private Location berlin = new Location();
    private Location berlinReturn = new Location();
    private VimInstance vimInstance0 = new VimInstance();
    private VimInstance vimInstance0Return = new VimInstance();
    private VimInstance vimInstance1 = new VimInstance();
    private VimInstance vimInstance1Return = new VimInstance();
    private Set<VimInstance> vimInstanceSet = new HashSet<VimInstance>();
    private NetworkServiceRecord nsr0 = new NetworkServiceRecord();
    private NetworkServiceRecord nsr1 = new NetworkServiceRecord();
    private NetworkServiceRecord nsr1Return = new NetworkServiceRecord();
    private NetworkServiceRecord nsr2 = new NetworkServiceRecord();
    private Set<NetworkServiceRecord> nsrSet = new HashSet<NetworkServiceRecord>();
    private VirtualNetworkFunctionRecord vnfr0 = new VirtualNetworkFunctionRecord();
    private VirtualNetworkFunctionRecord vnfr1 = new VirtualNetworkFunctionRecord();
    private Set<VirtualNetworkFunctionRecord> vnfrSet = new HashSet<VirtualNetworkFunctionRecord>();
    private Set<VirtualLinkDescriptor> vldSet = new HashSet<VirtualLinkDescriptor>();
    private VirtualLinkDescriptor vld1 = new VirtualLinkDescriptor();
    private Security security = new Security();
    private Set<Security> securitySet = new HashSet<Security>();
    private HighAvailability highAvailability = new HighAvailability();
    private VNFRecordDependency nsrVnfrDepReturn = new VNFRecordDependency();
    private Set<VNFRecordDependency> nsrVnfrDepSetReturn = new HashSet<VNFRecordDependency>();
    private Map<String, DependencyParameters> nsrDepParamMap = new HashMap();
    private Map<String, VNFCDependencyParameters> nsrVnfcParamMap = new HashMap();
    private Set<VirtualNetworkFunctionRecord> vnfrSetReturn = new HashSet<VirtualNetworkFunctionRecord>();
    private VirtualNetworkFunctionRecord vnfrReturn = new VirtualNetworkFunctionRecord();
    private Configuration requires = new Configuration();
    private Configuration provides = new Configuration();
    private Set<LifecycleEvent> lifecycleEventHistoryReturnSet = new HashSet<LifecycleEvent>();
    private Set<NetworkServiceRecord> nsrSetReturn = new HashSet<NetworkServiceRecord>();
    private DeploymentFlavour flavourReturn = new DeploymentFlavour();
    private Set<DeploymentFlavour> flavourSetReturn = new HashSet<DeploymentFlavour>();
    private Set<NFVImage> nfvImageSetReturn = new HashSet<NFVImage>();
    private NFVImage nfvImageReturn = new NFVImage();
    private Network networkReturn = new Network();
    private Set<Network> networkSetReturn = new HashSet<Network>();
    private Set<Subnet> subnetSetReturn = new HashSet<Subnet>();
    private Subnet subnetReturn = new Subnet();
    private Script installReturn = new Script();
    private Script startReturn = new Script();
    private Set<Script> scriptSetReturn = new HashSet<>();
    private Script packageScript = new Script();
    private NetworkServiceDeploymentFlavour serviceDeplFlavReturn = new NetworkServiceDeploymentFlavour();


    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        this.document = document("{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));



        this.mockMvc = MockMvcBuilders.standaloneSetup(restNetworkServiceDescriptor, restVirtualNetworkFunctionDescriptor,
                restNetworkServiceRecord, restVimInstances, restVNFPackage)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(this.document)
                .build();



        nsdSet.add(iperfNsd1);
        nsdSetReturn.add(iperfNsd1Return);

        iperfNsd1.setName("iperf-nsd");
        iperfNsd1.setVendor("FOKUS");
        iperfNsd1.setVersion("1");
        iperfNsd1.setVnfd(iperfVnfdSet);
        iperfNsd1.setVnf_dependency(iperfVnfdepSet);
        iperfNsd1.setVnf_dependency(iperfVnfdepSet);
        iperfNsd1.setVld(iperfVldSet);

        instReturn.setEvent(Event.INSTANTIATE);
        instReturn.setLifecycle_events(instScripts);
        instReturn.setId("164cc441-a58f-4c52-9270-457e2e2a21ca");

        startServerReturn.setEvent(Event.START);
        startServerReturn.setLifecycle_events(startServerScripts);
        startServerReturn.setId("556fbc91-6a32-419b-afa4-e7e93acf9977");

        startClientReturn.setEvent(Event.START);
        startClientReturn.setLifecycle_events(startClientScripts);
        startClientReturn.setId("3add5745-d582-43f4-b643-940c06022f11");

        leServerSetReturn.add(instReturn);
        leServerSetReturn.add(startServerReturn);
        leClientSetReturn.add(instReturn);
        leClientSetReturn.add(startClientReturn);

        iperfVnfdClientReturn.setLifecycle_event(leClientSetReturn);
        iperfVnfdClientReturn.setId("14a5be57-05fd-4747-a3ae-959ed643b44c");
        iperfVnfdClientReturn.setHb_version(0);
        iperfVnfdClientReturn.setName("iperf-client");
        iperfVnfdClientReturn.setVendor("FOKUS");
        iperfVnfdClientReturn.setVersion("1");
        iperfVnfdClientReturn.setVnffgd(new HashSet<VNFForwardingGraphDescriptor>());
        iperfVnfdClientReturn.setVld(new HashSet<VirtualLinkDescriptor>());
        iperfVnfdClientReturn.setMonitoring_parameter(new HashSet<String>());
        iperfVnfdClientReturn.setService_deployment_flavour(new HashSet<DeploymentFlavour>());
        iperfVnfdClientReturn.setAuto_scale_policy(new HashSet<AutoScalePolicy>());
        iperfVnfdClientReturn.setConnection_point(new HashSet<ConnectionPoint>());
        configurationReturn.setName("config_name");
        configurationReturn.setConfigurationParameters(confParametersReturn);
        configurationReturn.setId("760583a0-ff99-4754-b1c7-2a7b1cc5d7b3");
        configurationReturn.setVersion(1);
        configurationReturn.setConfigurationParameters(confParametersReturn);
        iperfVnfdClientReturn.setConfigurations(configurationReturn);
        confParReturn.setId("5969df32-3199-47d6-83cd-0b4da537dd0f");
        confParReturn.setVersion(0);
        confParReturn.setConfKey("answer");
        confParReturn.setValue("42");
        confParametersReturn.add(confParReturn);
        iperfVnfdClientReturn.setConfigurations(configurationReturn);
        vduClientReturn.setId("10ca7355-9261-42e0-8103-53beec9d6994");
        vduClientReturn.setVersion(1);
        imageSetReturn.add("ubuntu-14.04-server-cloudimg-amd64-disk1");
        vduClientReturn.setVm_image(imageSetReturn);
        vduClientReturn.setLifecycle_event(new HashSet<LifecycleEvent>());
        vduClientReturn.setFault_management_policy(new HashSet<VRFaultManagementPolicy>());
        vduClientReturn.setScale_in_out(2);
        vnfcReturn1.setId("ed2cc312-dad6-43a9-9245-c63b719e2126");
        vnfcReturn1.setVersion(1);
        vnfcReturn1.setId("5bcf9a48-c4d4-42e1-a202-e908ab364d66");
        vnfcReturn1.setVersion(1);
        vnfdConnectionPointReturn1.setVirtual_link_reference("private");
        vnfdConnectionPointReturn1.setFloatingIp("random");
        vnfdConnectionPointReturn1.setId("5f8cc194-4e70-415a-b569-481cb882383e");
        vnfdConnectionPointReturn1.setVersion(0);
        vnfdConnectionPointReturn2.setVirtual_link_reference("private");
        vnfdConnectionPointReturn2.setFloatingIp("random");
        vnfdConnectionPointReturn2.setId("98d00e1e-46bb-4eb2-9898-9b2ecec607af");
        vnfdConnectionPointReturn2.setVersion(1);
        vnfdConnectionPointSetReturn1.add(vnfdConnectionPointReturn1);
        vnfdConnectionPointSetReturn2.add(vnfdConnectionPointReturn2);
        vnfcReturn1.setConnection_point(vnfdConnectionPointSetReturn1);
        vnfcReturn2.setConnection_point(vnfdConnectionPointSetReturn1);
        vnfcSetReturn.add(vnfcReturn1);
        vnfcSetReturn.add(vnfcReturn2);
        vduClientReturn.setVnfc(vnfcSetReturn);
        vduClientReturn.setVnfc_instance(new HashSet<VNFCInstance>());
        vduClientReturn.setMonitoring_parameter(new HashSet<String>());
        vduClientReturn.setVimInstanceName("vim-instance");
        iperfVnfdClientReturn.setVdu(vduClientSetReturn);
        vlReturn.setConnection_points_references(new HashSet<String>());
        vlReturn.setId("b6be79b6-9f7c-494a-8543-7dec75bcfb22");
        vlReturn.setExtId("a3ae6958-34dc-305b-6903-3afc54bcad32");
        vlReturn.setHb_version(0);
        vlReturn.setQos(new HashSet<String>());
        vlReturn.setTest_access(new HashSet<String>());
        vlReturn.setName("private");
        vlReturn.setRoot_requirement("4");
        vlReturn.setLeaf_requirement("8");
        vlReturn.setConnectivity_type("connectivityType");
        vlSetReturn.add(vlReturn);
        iperfVnfdClientReturn.setVirtual_link(vlSetReturn);
        iperfVnfdClientReturn.setVdu_dependency(new HashSet<VDUDependency>());
        deplFlavReturn.setDf_constraint(new HashSet<String>());
        deplFlavReturn.setCostituent_vdu(new HashSet<CostituentVDU>());
        deplFlavReturn.setId("d56d907c-e6ba-4513-b911-1e6bdfe14691");
        deplFlavReturn.setVersion(0);
        deplFlavReturn.setFlavour_key("m1.small");
        deplFlavReturn.setRam(0);
        deplFlavReturn.setDisk(0);
        deplFlavReturn.setVcpus(0);
        iperfVnfdClientReturn.setDeployment_flavour(deploymentFlavSetReturn);
        iperfVnfdClientReturn.setManifest_file_security(new HashSet<Security>());
        iperfVnfdClientReturn.setType("client");
        iperfVnfdClientReturn.setEndpoint("generic");
        iperfVnfdClientReturn.setVnfPackageLocation("b42340e7-09c1-4768-ab09-5886ec3e3444");
        iperfVnfdClientReturn.setRequires(new HashSet<String>());
        iperfVnfdClientReturn.setProvides(new HashSet<String>());
        iperfVnfdClientReturn.setCyclicDependency(false);
        iperfVnfdClientReturn.setId("90f6f488-ade0-4ae4-976d-9e34553603ca");
        iperfVnfdClientReturn.setHb_version(1);
        iperfVnfdClientReturn.setVersion("0.1");
        iperfVnfdClientReturn.setVnffgd(new HashSet<VNFForwardingGraphDescriptor>());
        iperfVnfdClientReturn.setVld(new HashSet<VirtualLinkDescriptor>());
        iperfVnfdClientReturn.setMonitoring_parameter(new HashSet<String>());
        iperfVnfdClientReturn.setService_deployment_flavour(new HashSet<DeploymentFlavour>());
        iperfVnfdClientReturn.setAuto_scale_policy(new HashSet<AutoScalePolicy>());
        iperfVnfdClientReturn.setConnection_point(new HashSet<ConnectionPoint>());
        iperfVnfdClientReturn.setManifest_file("manifestFile");
        securitySet.add(security);
        iperfVnfdClientReturn.setManifest_file_security(securitySet);

        iperfVnfdServerReturn.setLifecycle_event(leServerSetReturn);
        iperfVnfdServerReturn.setId("c39b7146-ec08-46bd-ba3e-c9c9ff55aa9d");
        iperfVnfdServerReturn.setHb_version(0);
        iperfVnfdServerReturn.setVendor("FOKUS");
        iperfVnfdServerReturn.setVersion("1");
        iperfVnfdServerReturn.setVnffgd(new HashSet<VNFForwardingGraphDescriptor>());
        iperfVnfdServerReturn.setVld(new HashSet<VirtualLinkDescriptor>());
        iperfVnfdServerReturn.setMonitoring_parameter(new HashSet<String>());
        iperfVnfdServerReturn.setService_deployment_flavour(new HashSet<DeploymentFlavour>());
        iperfVnfdServerReturn.setAuto_scale_policy(new HashSet<AutoScalePolicy>());
        iperfVnfdServerReturn.setConnection_point(new HashSet<ConnectionPoint>());
        iperfVnfdServerReturn.setConfigurations(configurationReturn);
        iperfVnfdServerReturn.setConfigurations(configurationReturn);

        vduServerReturn.setId("56ea7355-9261-42e0-8193-53beec9d6514");
        vduServerReturn.setVersion(1);
        imageSetReturn.add("ubuntu-14.04-server-cloudimg-amd64-disk1");
        vduServerReturn.setVm_image(imageSetReturn);
        vduServerReturn.setLifecycle_event(new HashSet<LifecycleEvent>());
        vduServerReturn.setFault_management_policy(new HashSet<VRFaultManagementPolicy>());
        vduServerReturn.setScale_in_out(2);
        vduServerReturn.setName("iperf-server");
        vduServerReturn.setComputation_requirement("computationRequirement");
        vduServerReturn.setVirtual_memory_resource_element("virtualMemoryResourceElement");
        vduServerReturn.setVirtual_network_bandwidth_resource("virtualNetworkBandwidthResource");
        vduServerReturn.setVdu_constraint("vduConstraint");
        highAvailability.setGeoRedundancy(true);
        highAvailability.setRedundancyScheme("scheme");
        highAvailability.setResiliencyLevel(ResiliencyLevel.ACTIVE_STANDBY_STATEFUL);
        vduServerReturn.setHigh_availability(highAvailability);
        vnfdConnectionPointReturn3.setVirtual_link_reference("private");
        vnfdConnectionPointReturn3.setFloatingIp("random");
        vnfdConnectionPointReturn3.setId("5f8cc194-4e70-415a-b569-481cb882383e");
        vnfdConnectionPointReturn3.setVersion(0);
        vnfdConnectionPointReturn3.setType("type");
        vnfdConnectionPointSetReturn3.add(vnfdConnectionPointReturn3);
        vnfcReturn3.setConnection_point(vnfdConnectionPointSetReturn3);
        vnfcReturn3.setId("5e4cc574-4e70-415a-b209-481ad882383e");
        vnfcSetReturn2.add(vnfcReturn3);
        vduServerReturn.setVnfc(vnfcSetReturn2);
        vduServerReturn.setVnfc_instance(new HashSet<VNFCInstance>());
        vduServerReturn.setMonitoring_parameter(new HashSet<String>());
        vduServerReturn.setVimInstanceName("vim-instance");
        vduServerReturn.setHostname("iperfserver-435");
        vduServerSetReturn.add(vduServerReturn);
        iperfVnfdServerReturn.setVdu(vduServerSetReturn);
        iperfVnfdServerReturn.setVirtual_link(vlSetReturn);
        iperfVnfdServerReturn.setVdu_dependency(new HashSet<VDUDependency>());
        iperfVnfdServerReturn.setDeployment_flavour(deploymentFlavSetReturn);
        iperfVnfdServerReturn.setManifest_file_security(new HashSet<Security>());
        iperfVnfdServerReturn.setType("server");
        iperfVnfdServerReturn.setEndpoint("generic");
        iperfVnfdServerReturn.setVnfPackageLocation("a32340e7-45c1-4768-ab09-5826ec3e6594");
        iperfVnfdServerReturn.setRequires(new HashSet<String>());
        iperfVnfdServerReturn.setProvides(new HashSet<String>());
        iperfVnfdServerReturn.setCyclicDependency(false);
        iperfVnfdServerReturn.setId("40f3f488-ade0-4re4-976d-9e34253603aa");
        iperfVnfdServerReturn.setHb_version(1);
        iperfVnfdServerReturn.setName("iperfserver");
        iperfVnfdServerReturn.setVendor("fokus");
        iperfVnfdServerReturn.setVersion("0.1");
        iperfVnfdServerReturn.setVnffgd(new HashSet<VNFForwardingGraphDescriptor>());
        iperfVnfdServerReturn.setVld(new HashSet<VirtualLinkDescriptor>());
        iperfVnfdServerReturn.setMonitoring_parameter(new HashSet<String>());
        iperfVnfdServerReturn.setService_deployment_flavour(new HashSet<DeploymentFlavour>());
        iperfVnfdServerReturn.setAuto_scale_policy(new HashSet<AutoScalePolicy>());
        iperfVnfdServerReturn.setConnection_point(new HashSet<ConnectionPoint>());
        iperfVnfdServerReturn.setManifest_file("manifestFile");
        iperfVnfdServerReturn.setManifest_file_security(securitySet);


        iperfVnfdServerReturn.setLifecycle_event(leServerSetReturn);

        iperfVnfdSetReturn.add(iperfVnfdClientReturn);
        iperfVnfdSetReturn.add(iperfVnfdServerReturn);

        iperfNsd1Return.setName("iperf-nsd");
        iperfNsd1Return.setVendor("FOKUS");
        iperfNsd1Return.setVersion("1");
        iperfNsd1Return.setVnfd(iperfVnfdSet);
        iperfNsd1Return.setVnf_dependency(iperfVnfdepSet);
        iperfNsd1Return.setVnf_dependency(iperfVnfdepSet);
        iperfNsd1Return.setId("573aa807-b969-4bfd-95fa-f7abbe866bad");
        iperfNsd1Return.setVnffgd(new HashSet<VNFForwardingGraphDescriptor>());
        vld1.setName("private");
        vld1.setTest_access(new HashSet<String>());
        vld1.setQos(new HashSet<String>());
        vld1.setHb_version(0);
        vld1.setId("869c83ce-8c04-42f4-b54f-eddb64e7dfe4");
        vld1.setConnection(new HashSet<String>());
        vld1.setNumber_of_endpoints(0);
        vld1.setExtId("920c21da-3c04-42f3-b54f-etzb64f7dfe2");
        vld1.setRoot_requirement("4");
        vld1.setLeaf_requirement("8");
        vld1.setConnectivity_type("connectivityType");
        vld1.setVendor("FOKUS");
        vld1.setDescriptor_version("0.1");
        security.setId("965c21da-3c04-43f3-b54f-eadb34f7dda2");
        security.setVersion(1);
        vld1.setVld_security(security);
        vldSet.add(vld1);
        iperfNsd1Return.setVld(vldSet);
        iperfNsd1Return.setMonitoring_parameter(new HashSet<String>());
        iperfNsd1Return.setService_deployment_flavour(new HashSet<DeploymentFlavour>());
        iperfNsd1Return.setAuto_scale_policy(new HashSet<AutoScalePolicy>());
        iperfNsd1Return.setConnection_point(new HashSet<ConnectionPoint>());
        iperfNsd1Return.setVnfd(iperfVnfdSetReturn);
        iperfNsd1Return.setNsd_security(security);





        iperfVnfdSet.add(iperfVnfdServer);
        iperfVnfdSet.add(iperfVnfdClient);

        iperfVnfdServer.setName("iperf-server");
        iperfVnfdServer.setVersion("1");
        iperfVnfdServer.setVendor("FOKUS");
        iperfVnfdServer.setType("server");
        iperfVnfdServer.setEndpoint("generic");
        iperfVnfdServer.setConfigurations(configuration);
        iperfVnfdServer.setVdu(vduServerSet);
        iperfVnfdServer.setVirtual_link(vlSet);
        iperfVnfdServer.setLifecycle_event(leServerSet);
        iperfVnfdServer.setDeployment_flavour(deploymentFlavSet);

        configuration.setName("config_name");
        configuration.setConfigurationParameters(confParameters);

        confParameters.add(confPar);

        confPar.setConfKey("answer");
        confPar.setValue("42");

        vduServerSet.add(vduServer);

        vduServer.setVm_image(imageSet);
        vduServer.setVimInstanceName("vim-instance");
        vduServer.setScale_in_out(1);
        vduServer.setVnfc(vnfcSet);

        imageSet.add("name of an image");

        vnfcSet.add(vnfc);

        vnfc.setConnection_point(vnfdConnectionPointSet);

        vnfdConnectionPointSet.add(vnfdConnectionPoint);

        vnfdConnectionPoint.setVirtual_link_reference("networkA");

        vlSet.add(vl);

        vl.setName("networkA");



        instScripts.add("install.sh");
        startServerScripts.add("start-server.sh");
        startClientScripts.add("server_start-client.sh");

        inst.setEvent(Event.INSTANTIATE);
        inst.setLifecycle_events(instScripts);

        startServer.setEvent(Event.START);
        startServer.setLifecycle_events(startServerScripts);

        startClient.setEvent(Event.START);
        startClient.setLifecycle_events(startClientScripts);

        leServerSet.add(inst);
        leServerSet.add(startServer);
        leClientSet.add(inst);
        leClientSet.add(startClient);




        deploymentFlavSet.add(deplFlav);

        deplFlav.setFlavour_key("m1.small");
        deplFlav.setRam(2048);
        deplFlav.setDisk(5);
        deplFlav.setVcpus(2);



        iperfVnfdClient.setName("iperf-client");
        iperfVnfdClient.setVersion("1");
        iperfVnfdClient.setVendor("FOKUS");
        iperfVnfdClient.setType("client");
        iperfVnfdClient.setEndpoint("generic");
        iperfVnfdClient.setConfigurations(configuration);
        iperfVnfdClient.setVdu(vduClientSet);
        iperfVnfdClient.setVirtual_link(vlSet);
        iperfVnfdClient.setLifecycle_event(leClientSet);
        iperfVnfdClient.setDeployment_flavour(deploymentFlavSet);

        vduClientSet.add(vduClient);

        vduClient.setVm_image(imageSet);
        vduClient.setVimInstanceName("vim-instance");
        vduClient.setScale_in_out(2);
        vduClient.setVnfc(vnfcSet);

        iperfDep.setSource(iperfVnfdServer);
        iperfDep.setTarget(iperfVnfdClient);
        iperfDep.setParameters(depParamSet);

        depParamSet.add("networkA");

        vnfdSet.add(iperfVnfdClient);
        vnfdSet.add(iperfVnfdServer);

        vnfPackage0.setName("An example of a VNFPackage");
        vnfPackage0.setId("61a40e7c-a424-4d47-8413-532217ddcf4f");
        vnfPackage0.setImageLink("URL to the image file");
        vnfPackage0.setScriptsLink("URL to the place where the scripts can be downloaded");
        vnfPackage0.setImage(image);

        vnfPackage1.setName("An updated VNFPackage");
        vnfPackage1.setVersion(2);
        vnfPackage1.setImageLink("URL to the image file");
        vnfPackage1.setScriptsLink("URL to the place where the scripts can be downloaded");
        vnfPackage1.setImage(image);

        vnfPackage1Return.setName("An updated VNFPackage");
        vnfPackage1Return.setVersion(2);
        vnfPackage1Return.setId("61a40e7c-a424-4d47-8413-532217ddcf4f");
        vnfPackage1Return.setImageLink("URL to the image file");
        vnfPackage1Return.setScriptsLink("URL to the place where the scripts can be downloaded");
        vnfPackage1Return.setImage(nfvImageReturn);
        instReturn.setId("5034fc1a-83ad-23f9-7403-533fed9812ad");
        instReturn.setVersion(0);
        instReturn.setEvent(Event.INSTANTIATE);
        Script s = new Script();
        packageScript.setId("5034fc1a-83ad-23f9-7403-533fed9812ad");
        packageScript.setVersion(0);
        packageScript.setName("install.sh");
        byte[] payload = {35,33,47};
        packageScript.setPayload(payload);
        startReturn.setId("356af2d4-da3f-4517-4dc5-8723cf5cc935");
        scriptSetReturn.add(packageScript);
        vnfPackage1Return.setScripts(scriptSetReturn);

        vnfPackageSet.add(vnfPackage1Return);

        image.setName("ubuntu_server_image");

        scripts.add(install);
        scripts.add(start);

        install.setName("install.sh");
        start.setName("start.sh");

        berlin.setName("Berlin");
        berlin.setLatitude("52.525876");
        berlin.setLongitude("13.314400");

        berlinReturn.setName("Berlin");
        berlinReturn.setLatitude("52.525876");
        berlinReturn.setLongitude("13.314400");
        berlinReturn.setId("68374c3b-9d50-44bc-a435-9d3b96c226db");

        vimInstance0.setName("An example of a VimInstance");
        vimInstance0.setAuthUrl("A Url");
        vimInstance0.setPassword("12345");
        vimInstance0.setTenant("demo");
        vimInstance0.setType("openstack");
        vimInstance0.setUsername("User");
        vimInstance0.setLocation(berlin);

        vimInstance0Return.setName("An example of a VimInstance");
        vimInstance0Return.setAuthUrl("A Url");
        vimInstance0Return.setPassword("12345");
        vimInstance0Return.setTenant("demo");
        vimInstance0Return.setType("openstack");
        vimInstance0Return.setUsername("User");
        vimInstance0Return.setLocation(berlinReturn);
        // additional fields
        vimInstance0Return.setId("07241c3b-9d50-44ba-a495-9d3b96c226bd");
        vimInstance0Return.setKeyPair("test-key-pair");
        vimInstance0Return.setSecurityGroups(new HashSet<String>());
        flavourReturn.setId("de00be44-6bac-4e69-8e24-77d82d4b63ce");
        flavourReturn.setVersion(0);
        flavourReturn.setFlavour_key("m1.tiny");
        flavourReturn.setExtId("1");
        flavourReturn.setRam(512);
        flavourReturn.setDisk(1);
        flavourReturn.setVcpus(1);
        flavourSetReturn.add(flavourReturn);
        vimInstance0Return.setFlavours(flavourSetReturn);
        nfvImageReturn.setId("ab90ae11-267e-4ee6-b623-0d3d42f910ec");
        nfvImageReturn.setVersion(0);
        nfvImageReturn.setExtId("30ecc9a1-53bb-451a-a9f3-adf460d4cb94");
        nfvImageReturn.setName("ImageName");
        nfvImageReturn.setMinRam(0);
        nfvImageReturn.setMinCPU("0");
        nfvImageReturn.setMinDiskSpace(0);
        nfvImageReturn.setIsPublic(false);
        nfvImageReturn.setDiskFormat("OPTIONAL.OF(QCOW2)");
        nfvImageReturn.setContainerFormat("OPTIONAL.OF(BARE)");
        nfvImageReturn.setCreated(new Date());
        nfvImageReturn.setUpdated(new Date());
        nfvImageSetReturn.add(nfvImageReturn);
        vimInstance0Return.setImages(nfvImageSetReturn);
        networkReturn.setId("3b772bbb-45fe-4f06-a0e5-c21b9bbe0367");
        networkReturn.setVersion(0);
        networkReturn.setName("private1");
        networkReturn.setExtId("26e791ac-5215-4663-b393-3303e310c42e");
        networkReturn.setExternal(false);
        networkReturn.setShared(false);
        subnetReturn.setId("49920742-992c-4097-aa78-0592498b1d40");
        subnetReturn.setVersion(0);
        subnetReturn.setName("private_subnet");
        subnetReturn.setExtId("fa3a5781-dee1-4b96-8aa9-01488a60acaa");
        subnetReturn.setNetworkId("26e791ac-5215-4663-b393-3303e310c42e");
        subnetReturn.setCidr("192.168.216.0/24");
        subnetReturn.setGatewayIp("192.168.216.1");
        subnetSetReturn.add(subnetReturn);
        networkReturn.setSubnets(subnetSetReturn);
        networkSetReturn.add(networkReturn);
        vimInstance0Return.setNetworks(networkSetReturn);

        vimInstance1.setName("An updated VimInstance");
        vimInstance1.setAuthUrl("A Url");
        vimInstance1.setPassword("123456789");
        vimInstance1.setTenant("demo");
        vimInstance1.setType("Type2");
        vimInstance1.setUsername("User");
        vimInstance1.setLocation(berlin);
        //additional fields
        vimInstance1Return.setName("An updated VimInstance");
        vimInstance1Return.setAuthUrl("A Url");
        vimInstance1Return.setPassword("123456789");
        vimInstance1Return.setTenant("demo");
        vimInstance1Return.setType("Type2");
        vimInstance1Return.setUsername("User");
        vimInstance1Return.setLocation(berlinReturn);
        vimInstance1Return.setId("07241c3b-9d50-44ba-a495-9d3b96c226bd");
        vimInstance1Return.setKeyPair("test-key-pair");
        vimInstance1Return.setSecurityGroups(new HashSet<String>());
        vimInstance1Return.setFlavours(flavourSetReturn);
        vimInstance1Return.setImages(nfvImageSetReturn);
        vimInstance1Return.setNetworks(networkSetReturn);

        vimInstanceSet.add(vimInstance0Return);

        nsr0.setName("An example NSR");
        nsr0.setVersion("1.0");
        nsr0.setVendor("FOKUS");
        nsr0.setId("66046d77-aade-4f14-ad39-f2976532e5f2");
        nsr0.setDescriptor_reference("c60ec37a-654a-4605-93fb-5a1932db6ecf");

        nsr1.setName("An example NSR");
        nsr1.setVersion("1.0");
        nsr1.setDescriptor_reference("c60ec37a-654a-4605-93fb-5a1932db6ecf");
        nsr1.setVendor("FOKUS");
        nsr1.setStatus(Status.ACTIVE);
        nsr1.setVlr(new HashSet<VirtualLinkRecord>());
        nsr1.setVnf_dependency(new HashSet<VNFRecordDependency>());
        nsr1.setVnfr(new HashSet<VirtualNetworkFunctionRecord>());

        nsr1Return.setName("An example NSR");
        nsr1Return.setId("4da46d77-a46e-4e24-ab57-f2976532e5f1");
        nsr1Return.setVersion("1.0");
        nsr1Return.setDescriptor_reference("c60ec37a-654a-4605-93fb-5a1932db6ecf");
        nsr1Return.setVendor("FOKUS");
        nsr1Return.setStatus(Status.ACTIVE);
        nsr1Return.setVlr(new HashSet<VirtualLinkRecord>());
        nsr1Return.setVnfr(new HashSet<VirtualNetworkFunctionRecord>());
        // additional fields
        nsr1Return.setId("66046d77-aade-4f14-ad39-f2976532e5f2");
        nsr1Return.setAuto_scale_policy(new HashSet<AutoScalePolicy>());
        nsr1Return.setConnection_point(new HashSet<ConnectionPoint>());
        nsr1Return.setMonitoring_parameter(new HashSet<String>());

        vnfrReturn.setId("293e6a52-c6df-40c0-8f8a-58911bb9319b");
        vnfrReturn.setHb_version(4);
        vnfrReturn.setAuto_scale_policy(new HashSet<AutoScalePolicy>());
        vnfrReturn.setConnection_point(new HashSet<ConnectionPoint>());
        vnfrReturn.setDeployment_flavour_key("m1.small");
        vnfrReturn.setConfigurations(configurationReturn);
        vnfrReturn.setLifecycle_event(leServerSetReturn);
        LifecycleEvent granted = new LifecycleEvent();
        granted.setId("07c1ac29-0e2e-4d9f-b8a2-2cc1f3684328");
        granted.setVersion(1);
        granted.setEvent(Event.GRANTED);
        granted.setLifecycle_events(new LinkedList<String>());
        lifecycleEventHistoryReturnSet.add(granted);
        vnfrReturn.setLifecycle_event_history(lifecycleEventHistoryReturnSet);
        vnfrReturn.setMonitoring_parameter(new HashSet<String>());
        vnfrReturn.setVdu(vduServerSetReturn);
        vnfrReturn.setVendor("FOKUS");
        vnfrReturn.setVersion("0.1");
        vnfrReturn.setVirtual_link(vlSetReturn);
        vnfrReturn.setParent_ns_id("f9ba54f2-a825-47b0-9c3e-e00275faf169");
        vnfrReturn.setDescriptor_reference("0a60d551-5498-40cc-a0ef-279a10e72228");
        vnfrReturn.setConnected_external_virtual_link(new HashSet<VirtualLinkRecord>());
        Set<String> vnfrAddress = new HashSet<String>();
        vnfrAddress.add("10.253.4.7");
        vnfrReturn.setVnf_address(vnfrAddress);
        vnfrReturn.setStatus(Status.ACTIVE);
        vnfrReturn.setNotification(new HashSet<String>());
        vnfrReturn.setRuntime_policy_info(new HashSet<String>());
        vnfrReturn.setName("iperfserver");
        vnfrReturn.setType("server");
        vnfrReturn.setEndpoint("generic");
        vnfrReturn.setTask("start");
        requires.setId("fe0c2cb2-e251-415a-b894-d04ddf8970dc");
        requires.setVersion(0);
        requires.setConfigurationParameters(new HashSet<ConfigurationParameter>());
        requires.setName("requires");
        vnfrReturn.setRequires(requires);
        provides.setId("5b36d727-0a95-4be0-958e-7c94857d5c70");
        provides.setVersion(0);
        provides.setConfigurationParameters(new HashSet<ConfigurationParameter>());
        provides.setName("provides");
        vnfrReturn.setProvides(provides);
        vnfrReturn.setCyclicDependency(false);
        vnfrReturn.setPackageId("fcaa8993-b9d0-46c7-a258-85dd847c901f");
        vnfrReturn.setLocalization("berlin");
        vnfrReturn.setVnfm_id("46046d37-aade-4f14-ad39-f2976532e4d8");
        vnfrReturn.setAudit_log("auditLog");

        vnfrSetReturn.add(vnfrReturn);
        nsr1Return.setVnfr(vnfrSetReturn);
        nsrVnfrDepReturn.setId("46046d37-aade-4f14-ad39-f2976532e4d8");
        nsrVnfrDepReturn.setVersion(1);
        nsrVnfrDepReturn.setTarget("iperfclient");
        DependencyParameters param1 = new DependencyParameters();
        param1.setId("76046e27-aade-4f14-ad39-f2976532e5f2");
        param1.setVersion(6);
        Map<String, String> paramMap1 = new HashMap<String, String>();
        paramMap1.put("private", "");
        param1.setParameters(paramMap1);
        nsrDepParamMap.put("server", param1);
        nsrVnfrDepReturn.setParameters(nsrDepParamMap);
        VNFCDependencyParameters param2 = new VNFCDependencyParameters();
        param2.setId("01456d77-aade-4f14-ad39-f2976532e5e2");
        param2.setVersion(4);
        param2.setVnfcId("66046d77-aade-4f14-ad39-f2976532e5f2");
        Map<String, DependencyParameters> paramMap2 = new HashMap<String, DependencyParameters>();
        DependencyParameters dependencyParameters = new DependencyParameters();
        dependencyParameters.setId("77e44026-b609-41fb-8d24-7acf652c6488");
        dependencyParameters.setVersion(6);
        Map<String, String> paramMap3 = new HashMap<String, String>();
        paramMap1.put("private", "10.231.5.1");
        dependencyParameters.setParameters(paramMap3);
        paramMap2.put("51e0f1a0-46f1-4fd2-8737-4dd51d4b57ed", dependencyParameters);
        param2.setParameters(paramMap2);
        nsrVnfcParamMap.put("server", param2);
        nsrVnfrDepReturn.setVnfcParameters(nsrVnfcParamMap);
        Map<String, String> idTypeMap = new HashMap<>();
        idTypeMap.put("iperfserver", "server");
        nsrVnfrDepReturn.setIdType(idTypeMap);
        nsrVnfrDepSetReturn.add(nsrVnfrDepReturn);
        nsr1Return.setVnf_dependency(nsrVnfrDepSetReturn);
        nsr1Return.setLifecycle_event(new HashSet<LifecycleEvent>());
        nsr1Return.setVnffgr(new HashSet<VNFForwardingGraphRecord>());
        nsr1Return.setPnfr(new HashSet<PhysicalNetworkFunctionRecord>());
        nsr1Return.setFaultManagementPolicy(new HashSet<FaultManagementPolicy>());
        nsr1Return.setLifecycle_event_history(new HashSet<LifecycleEvent>());
        nsr1Return.setAudit_log("auditLog");
        nsr1Return.setNotification("notification");
        nsr1Return.setRuntime_policy_info("runtimePolicyInfo");
        nsr1Return.setResource_reservation("resourceReservation");
        serviceDeplFlavReturn.setId("5927dc9a-93cd-28de-2947-d382d8fc3840");
        serviceDeplFlavReturn.setVersion(0);
        serviceDeplFlavReturn.setFlavour_key("flavourKey");
        serviceDeplFlavReturn.setExtId("1");
        serviceDeplFlavReturn.setRam(512);
        serviceDeplFlavReturn.setDisk(1);
        serviceDeplFlavReturn.setVcpus(1);
        CostituentVNF constVnf = new CostituentVNF();
        constVnf.setId("492da364-25d7-7654-da74-128d8caf4721");
        constVnf.setAffinity("affinity");
        constVnf.setCapability("capability");
        constVnf.setNumber_of_instances(2);
        constVnf.setRedundancy_model(RedundancyModel.ACTIVE);
        constVnf.setVnf_flavour_id_reference("5927dc9a-93cd-28de-2947-d382d8fc3840");
        constVnf.setVnf_reference("485139cd-74dc-af3d-7593-12d78c51af12");
        Set<CostituentVNF> constVnfSet = new HashSet<>();
        constVnfSet.add(constVnf);
        serviceDeplFlavReturn.setConstituent_vnf(constVnfSet);
        nsr1Return.setService_deployment_flavour(serviceDeplFlavReturn);


        nsr2.setName("An example NSR");
        nsr2.setVersion("1.0");
        nsr2.setDescriptor_reference("c60ec37a-654a-4605-93fb-5a1932db6ecf");
        nsr2.setVendor("FOKUS");
        nsr2.setStatus(Status.ACTIVE);
        nsr2.setVlr(new HashSet<VirtualLinkRecord>());
        nsr2.setVnf_dependency(new HashSet<VNFRecordDependency>());
        nsr2.setVnfr(vnfrSet);
        nsr2.setId("5e074545-0a81-4de8-8494-eb67173ec565");


        nsrSet.add(nsr0);

        nsrSetReturn.add(nsr1Return);


        vnfr0.setName("iperf-server");
        vnfr0.setId("2135f315-1772-4ad8-85c8-caa209400ef0");
        vnfr0.setEndpoint("generic");
        vnfr0.setDescriptor_reference("f9382f4e-9a42-43c7-bab0-3915719c6fca");
        vnfr0.setVersion("1");
        vnfr0.setVendor("FOKUS");
        vnfr0.setLifecycle_event(leServerSet);

        vnfr1.setName("iperf-client");
        vnfr1.setId("2135f315-1772-4ad8-85c8-caa209400ef0");
        vnfr1.setEndpoint("generic");
        vnfr1.setDescriptor_reference("f9382f4e-9a42-43c7-bab0-3915719c6fca");
        vnfr1.setVersion("1");
        vnfr1.setVendor("FOKUS");
        vnfr1.setLifecycle_event(leClientSet);


        vnfrSet.add(vnfr0);
        vnfrSet.add(vnfr1);


    }




    @Test
    public void nsdCreateExample() throws Exception, BadFormatException {
        when(networkServiceDescriptorManagement.onboard(iperfNsd1)).thenReturn(iperfNsd1);

        this.mockMvc.perform(post("/api/v1/ns-descriptors").
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(gson.toJson(iperfNsd1))).
                andExpect(status().isCreated()).
                andDo(document("nsd-create-example", requestFields(
                        fieldWithPath("name").description("The NSD's name"),
                        fieldWithPath("vendor").description("The vendor of the NSD"),
                        fieldWithPath("version").description("The NSD's version"),
                        fieldWithPath("vld").description("An array of VirtualLinkDescriptors"),
                        fieldWithPath("vnf_dependency").description("An array of VNFDependencies"),
                        fieldWithPath("vnfd").description("An array of <<resources-VirtualNetworkFunctionDescriptor,VirtualNetworkFunctionDescriptors>>"),
                        fieldWithPath("hb_version").type(JsonFieldType.STRING).description("_"),
                        fieldWithPath("enabled").description("_")
                ))).
                andDo(document("nsd-create-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }


    @Test
    public void nsdGetAllExample() throws  Exception {
        when(networkServiceDescriptorManagement.query()).thenReturn(nsdSetReturn);


        this.mockMvc.perform(get("/api/v1/ns-descriptors")).
                andExpect(status().isOk()).
                andDo(document("nsd-get-all-example", responseFields(
                        fieldWithPath("[]").description("The array of NetworkServiceDescriptors")
                ))).
                andDo(document("nsd-get-all-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }



    @Test
    public void nsdGetExample() throws Exception {
        when(networkServiceDescriptorManagement.query("55555c52-f952-430c-b093-45acb2bbf50e")).thenReturn(iperfNsd1Return);

        iperfNsd1.setId("55555c52-f952-430c-b093-45acb2bbf50e");


        this.mockMvc.perform(get("/api/v1/ns-descriptors/55555c52-f952-430c-b093-45acb2bbf50e")).
                andExpect(status().isOk()).
                andDo(document("nsd-get-example", responseFields(
                        fieldWithPath("auto_scale_policy").type(JsonFieldType.ARRAY).description("_"),
                        fieldWithPath("connection_point").type(JsonFieldType.ARRAY).description("_"),
                        fieldWithPath("enabled").description("_"),
                        fieldWithPath("hb_version").description("_"),
                        fieldWithPath("id").description("The NSD's id"),
                        fieldWithPath("monitoring_parameter").type(JsonFieldType.ARRAY).description("Used for fault management"),
                        fieldWithPath("name").description("The NSD's name"),
                        fieldWithPath("nsd_security").type(JsonFieldType.OBJECT).description("_"),
                        fieldWithPath("pnfd").description("_"),
                        fieldWithPath("service_deployment_flavour").type(JsonFieldType.ARRAY).description("_"),
                        fieldWithPath("vendor").description("The vendor of the NSD"),
                        fieldWithPath("version").description("The NSD's version"),
                        fieldWithPath("vld").description("An array of VirtualLinkDescriptors"),
                        fieldWithPath("vnf_dependency").description("An array of VNFDependencies"),
                        fieldWithPath("vnfd").description("An array of <<resources-VirtualNetworkFunctionDescriptor, VirtualNetworkFunctionDescriptors>>"),
                        fieldWithPath("vnffgd").type(JsonFieldType.ARRAY).description("_")
                ))).
                andDo(document("nsd-get-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

        iperfNsd1.setId(null);
    }



    @Test
    public void nsdUpdateExample() throws Exception {
        when(networkServiceDescriptorManagement.update(any(NetworkServiceDescriptor.class))).
                thenReturn(iperfNsd1Return);

        this.mockMvc.perform(put("/api/v1/ns-descriptors/55555c52-f952-430c-b093-45acb2bbf50e").
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(gson.toJson(iperfNsd1))).
                andExpect(status().isAccepted()).
                andDo(document("nsd-update-example", requestFields(
                        fieldWithPath("enabled").description("_"),
                        fieldWithPath("hb_version").description("_"),
                        fieldWithPath("name").description("The NSD's name"),
                        fieldWithPath("vendor").description("The vendor of the NSD"),
                        fieldWithPath("version").description("The NSD's version"),
                        fieldWithPath("vld").description("An array of VirtualLinkDescriptors"),
                        fieldWithPath("vnf_dependency").description("An array of VNFDependencies"),
                        fieldWithPath("vnfd").description("An array of <<resources-VirtualNetworkFunctionDescriptor,VirtualNetworkFunctionDescriptors>>")
                ))).
                andDo(document("nsd-update-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }


    @Test
    public void nsdDeleteExample() throws Exception {
        Mockito.doNothing().when(networkServiceDescriptorManagement).delete("55555c52-f952-430c-b093-45acb2bbf50e");

        this.mockMvc.perform(delete("/api/v1/ns-descriptors/55555c52-f952-430c-b093-45acb2bbf50e")).
                andExpect(status().isNoContent());
    }



    @Test
    public void nsdCreateVNFDExample() throws Exception {
        when(networkServiceDescriptorManagement.addVnfd(any(VirtualNetworkFunctionDescriptor.class),
                eq("55555c52-f952-430c-b093-45acb2bbf50e")))
                .thenReturn(iperfVnfdServerReturn);

        this.mockMvc.perform(post("/api/v1/ns-descriptors/55555c52-f952-430c-b093-45acb2bbf50e/vnfdescriptors/").
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(gson.toJson(iperfVnfdServer))).
                andExpect(status().isCreated()).
                andDo(document("nsd-create-v-n-f-d-example", requestFields(
                        fieldWithPath("configurations").type(JsonFieldType.OBJECT).description("Configuration object to provide parameters to the Network Service"),
                        fieldWithPath("cyclicDependency").description("_"),
                        fieldWithPath("deployment_flavour").description("The deployment flavour of this VNFD"),
                        fieldWithPath("endpoint").type(JsonFieldType.STRING).description("The type of Vnfm in charge of managing this VNF"),
                        fieldWithPath("hb_version").description("_"),
                        fieldWithPath("lifecycle_event").type(JsonFieldType.ARRAY).description("The lifecycle events that should be executed on the VNF triggered by the vnfm"),
                        fieldWithPath("name").description("The name of the VNFD"),
                        fieldWithPath("type").description("The type of the VNFD"),
                        fieldWithPath("vdu").description("An aray of <<components-VirtualDeploymentUnit, VirtualDeploymentUnits>>"),
                        fieldWithPath("vendor").type(JsonFieldType.STRING).description("The vendor of the VNFD"),
                        fieldWithPath("virtual_link").description("Array of VirtualLinks, that represent the type of network connectivity mandated by the VNF vendor between two or more ConnectionPoints"),
                        fieldWithPath("version").description("The version of the VNFD")
                ))).
                andDo(document("nsd-create-v-n-f-d-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }



    @Test
    public void nsdGetAllVNFDExample() throws Exception {
        when(networkServiceDescriptorManagement.query("55555c52-f952-430c-b093-45acb2bbf50e")).
                thenReturn(iperfNsd1Return);

        this.mockMvc.perform(get("/api/v1/ns-descriptors/55555c52-f952-430c-b093-45acb2bbf50e/vnfdescriptors")).
                andExpect(status().isOk()).
                andDo(document("nsd-get-all-v-n-f-d-example", responseFields(
                        fieldWithPath("[]").description("The array containing the VNFDs of the NSD with the specified id")
                ))).
                andDo(document("nsd-get-all-v-n-f-d-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

    }


    @Test
    public void nsdGetVNFDExample() throws Exception {
        when(networkServiceDescriptorManagement.getVirtualNetworkFunctionDescriptor("55555c52-f952-430c-b093-45acb2bbf50e",
                "faaa910d-457d-4148-b4ea-7373b515febc")).
                thenReturn(iperfVnfdServerReturn);

        this.mockMvc.perform(get("/api/v1/ns-descriptors/55555c52-f952-430c-b093-45acb2bbf50e/vnfdescriptors/faaa910d-457d-4148-b4ea-7373b515febc")).
                andExpect(status().isOk()).
                andDo(document("nsd-get-v-n-f-d-example", responseFields(
                        fieldWithPath("auto_scale_policy").type(JsonFieldType.ARRAY).description("_"),
                        fieldWithPath("configurations").type(JsonFieldType.OBJECT).description("Configuration object to provide parameters to the Network Service"),
                        fieldWithPath("connection_point").description("_"),
                        fieldWithPath("deployment_flavour").type(JsonFieldType.ARRAY).description("The deployment flavour of this VNFD"),
                        fieldWithPath("endpoint").type(JsonFieldType.STRING).description("The type of the Vnfm in charge of managing this VNF"),
                        fieldWithPath("hb_version").description("_"),
                        fieldWithPath("id").description("The VNFD's id"),
                        fieldWithPath("lifecycle_event").description("The lifecycle events that should be executed on the VNF triggered by the vnfm"),
                        fieldWithPath("manifest_file").type(JsonFieldType.STRING).description("_"),
                        fieldWithPath("manifest_file_security").description("_"),
                        fieldWithPath("monitoring_parameter").type(JsonFieldType.ARRAY).description("Used for fault management"),
                        fieldWithPath("name").description("The VNFD's name"),
                        fieldWithPath("provides").description("_"),
                        fieldWithPath("requires").description("_"),
                        fieldWithPath("service_deployment_flavour").type(JsonFieldType.ARRAY).description("_"),
                        fieldWithPath("type").description("The type of the VNFD"),
                        fieldWithPath("vendor").description("The vendor of the VNFD"),
                        fieldWithPath("version").description("The VNFD's version"),
                        fieldWithPath("virtual_link").description("Array of VirtualLinks, that represent the type of network connectivity mandated by the VNF vendor between two or more ConnectionPoints"),
                        fieldWithPath("vld").description("An array of VirtualLinkDescriptors"),
                        fieldWithPath("vnffgd").type(JsonFieldType.ARRAY).description("_"),
                        fieldWithPath("vnfPackageLocation").type(JsonFieldType.STRING).description("The url where the scripts for the lifecycle events are stored"),
                        fieldWithPath("vdu").description("An aray of <<components-VirtualDeploymentUnit, VirtualDeploymentUnits>>"),
                        fieldWithPath("vdu_dependency").description("_")
                ))).
                andDo(document("nsd-get-v-n-f-d-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }




    @Test
    public void nsdUpdateVNFDExample() throws Exception {
        when(networkServiceDescriptorManagement.updateVNF(eq("55555c52-f952-430c-b093-45acb2bbf50e"), eq("faaa910d-457d-4148-b4ea-7373b515febc"),
                any(VirtualNetworkFunctionDescriptor.class))).
                thenReturn(iperfVnfdServerReturn);

        this.mockMvc.perform(put("/api/v1/ns-descriptors/55555c52-f952-430c-b093-45acb2bbf50e/vnfdescriptors/faaa910d-457d-4148-b4ea-7373b515febc").
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(gson.toJson(iperfVnfdServer))).
                andExpect(status().isAccepted()).
                andDo(document("nsd-update-v-n-f-d-example", requestFields(
                        fieldWithPath("configurations").type(JsonFieldType.OBJECT).description("Configuration object to provide parameters to the Network Service"),
                        fieldWithPath("deployment_flavour").type(JsonFieldType.ARRAY).description("The deployment flavour of this VNFD"),
                        fieldWithPath("cyclicDependency").description("_"),
                        fieldWithPath("endpoint").type(JsonFieldType.STRING).description("The type of the Vnfm in charge of managing this VNF"),
                        fieldWithPath("hb_version").description("_"),
                        fieldWithPath("lifecycle_event").description("The lifecycle events that should be executed on the VNF triggered by the vnfm"),
                        fieldWithPath("name").description("The name of the VNFD"),
                        fieldWithPath("type").description("The type of the VNFD"),
                        fieldWithPath("vdu").description("An aray of <<components-VirtualDeploymentUnit, VirtualDeploymentUnits>>"),
                        fieldWithPath("vendor").type(JsonFieldType.STRING).description("The vendor of the VNFD"),
                        fieldWithPath("version").description("The version of the VNFD"),
                        fieldWithPath("virtual_link").description("Array of VirtualLinks, that represent the type of network connectivity mandated by the VNF vendor between two or more ConnectionPoints")
                ))).
                andDo(document("nsd-update-v-n-f-d-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }


    @Test
    public void nsdDeleteVNFDExample() throws Exception {
        Mockito.doNothing().when(networkServiceDescriptorManagement).
                deleteVnfDescriptor("55555c52-f952-430c-b093-45acb2bbf50e", "faaa910d-457d-4148-b4ea-7373b515febc");

        this.mockMvc.perform(delete("/api/v1/ns-descriptors/55555c52-f952-430c-b093-45acb2bbf50e/vnfdescriptors/faaa910d-457d-4148-b4ea-7373b515febc")).
                andExpect(status().isNoContent());
    }



    @Test
    public void createVNFDExample() throws Exception {
        when(vnfdManagement.add(iperfVnfdClient)).thenReturn(iperfVnfdClient);

        this.mockMvc.perform(post("/api/v1/vnf-descriptors").
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(gson.toJson(iperfVnfdClient))).
                andExpect(status().isCreated()).
                andDo(document("create-v-n-f-d-example", requestFields(
                        fieldWithPath("configurations").type(JsonFieldType.OBJECT).description("Configuration object to provide parameters to the Network Service"),
                        fieldWithPath("cyclicDependency").description("_"),
                        fieldWithPath("deployment_flavour").description("The deployment flavour of this VNFD"),
                        fieldWithPath("endpoint").type(JsonFieldType.STRING).description("The type of Vnfm in charge of managing this VNF"),
                        fieldWithPath("hb_version").description("_"),
                        fieldWithPath("lifecycle_event").type(JsonFieldType.ARRAY).description("The lifecycle events that should be executed on the VNF triggered by the vnfm"),
                        fieldWithPath("name").description("The name of the VNFD"),
                        fieldWithPath("type").description("The type of the VNFD"),
                        fieldWithPath("vdu").description("An aray of <<components-VirtualDeploymentUnit, VirtualDeploymentUnits>>"),
                        fieldWithPath("vendor").type(JsonFieldType.STRING).description("The vendor of the VNFD"),
                        fieldWithPath("virtual_link").description("Array of VirtualLinks, that represent the type of network connectivity mandated by the VNF vendor between two or more ConnectionPoints"),
                        fieldWithPath("version").description("The version of the VNFD")
                ))).
                andDo(document("create-v-n-f-d-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }



    @Test
    public void getAllVNFDExample() throws Exception {
        when(vnfdManagement.query()).thenReturn(iperfVnfdSetReturn);

        this.mockMvc.perform(get("/api/v1/vnf-descriptors")).
                andExpect(status().isOk()).
                andDo(document("get-all-v-n-f-d-example", responseFields(
                        fieldWithPath("[]").description("The array containing every VNFD")
                ))).
                andDo(document("get-all-v-n-f-d-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));

    }


    @Test
    public void getVNFDExample() throws Exception {
        when(vnfdManagement.query("55553c52-f952-430c-b693-45aab2bbf58e")).
                thenReturn(iperfVnfdClientReturn);

        this.mockMvc.perform(get("/api/v1/vnf-descriptors/55553c52-f952-430c-b693-45aab2bbf58e")).
                andExpect(status().isOk()).
                andDo(document("get-v-n-f-d-example", responseFields(
                        fieldWithPath("auto_scale_policy").type(JsonFieldType.ARRAY).description("_"),
                        fieldWithPath("configurations").type(JsonFieldType.OBJECT).description("Configuration object to provide parameters to the Network Service"),
                        fieldWithPath("connection_point").description("_"),
                        fieldWithPath("deployment_flavour").type(JsonFieldType.ARRAY).description("The deployment flavour of this VNFD"),
                        fieldWithPath("endpoint").type(JsonFieldType.STRING).description("The type of the Vnfm in charge of managing this VNF"),
                        fieldWithPath("hb_version").description("_"),
                        fieldWithPath("id").description("The VNFD's id"),
                        fieldWithPath("lifecycle_event").description("The lifecycle events that should be executed on the VNF triggered by the vnfm"),
                        fieldWithPath("manifest_file").type(JsonFieldType.STRING).description("_"),
                        fieldWithPath("manifest_file_security").description("_"),
                        fieldWithPath("monitoring_parameter").type(JsonFieldType.ARRAY).description("Used for fault management"),
                        fieldWithPath("name").description("The VNFD's name"),
                        fieldWithPath("provides").description("_"),
                        fieldWithPath("requires").description("_"),
                        fieldWithPath("service_deployment_flavour").type(JsonFieldType.ARRAY).description("_"),
                        fieldWithPath("type").description("The type of the VNFD"),
                        fieldWithPath("vendor").description("The vendor of the VNFD"),
                        fieldWithPath("version").description("The VNFD's version"),
                        fieldWithPath("virtual_link").description("Array of VirtualLinks, that represent the type of network connectivity mandated by the VNF vendor between two or more ConnectionPoints"),
                        fieldWithPath("vld").description("An array of VirtualLinkDescriptors"),
                        fieldWithPath("vnffgd").type(JsonFieldType.ARRAY).description("_"),
                        fieldWithPath("vnfPackageLocation").type(JsonFieldType.STRING).description("The url where the scripts for the lifecycle events are stored"),
                        fieldWithPath("vdu").description("An aray of <<components-VirtualDeploymentUnit, VirtualDeploymentUnits>>"),
                        fieldWithPath("vdu_dependency").description("_")
                ))).
                andDo(document("get-v-n-f-d-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }




    @Test
    public void updateVNFDExample() throws Exception {
        when(vnfdManagement.update(any(VirtualNetworkFunctionDescriptor.class), eq("faba910d-449d-4146-b4ea-7373c515aebc"))).
                thenReturn(iperfVnfdClientReturn);

        this.mockMvc.perform(put("/api/v1/vnf-descriptors/faba910d-449d-4146-b4ea-7373c515aebc").
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(gson.toJson(iperfVnfdClient))).
                andExpect(status().isAccepted()).
                andDo(document("update-v-n-f-d-example", requestFields(
                        fieldWithPath("configurations").type(JsonFieldType.OBJECT).description("Configuration object to provide parameters to the Network Service"),
                        fieldWithPath("deployment_flavour").type(JsonFieldType.ARRAY).description("The deployment flavour of this VNFD"),
                        fieldWithPath("cyclicDependency").description("_"),
                        fieldWithPath("endpoint").type(JsonFieldType.STRING).description("The type of the Vnfm in charge of managing this VNF"),
                        fieldWithPath("hb_version").description("_"),
                        fieldWithPath("lifecycle_event").description("The lifecycle events that should be executed on the VNF triggered by the vnfm"),
                        fieldWithPath("name").description("The name of the VNFD"),
                        fieldWithPath("type").description("The type of the VNFD"),
                        fieldWithPath("vdu").description("An aray of <<components-VirtualDeploymentUnit, VirtualDeploymentUnits>>"),
                        fieldWithPath("vendor").type(JsonFieldType.STRING).description("The vendor of the VNFD"),
                        fieldWithPath("version").description("The version of the VNFD"),
                        fieldWithPath("virtual_link").description("Array of VirtualLinks, that represent the type of network connectivity mandated by the VNF vendor between two or more ConnectionPoints")
                ))).
                andDo(document("update-v-n-f-d-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }


    @Test
    public void deleteVNFDExample() throws Exception {
        Mockito.doNothing().when(vnfdManagement).
                delete("faba910d-449d-4146-b4ea-7373c515aebc");

        this.mockMvc.perform(delete("/api/v1/vnf-descriptors/faba910d-449d-4146-b4ea-7373c515aebc")).
                andExpect(status().isNoContent());
    }





    @Test
    public void nsrCreateExample() throws Exception, QuotaExceededException, BadFormatException {
        when(networkServiceRecordManagement.onboard(any(NetworkServiceDescriptor.class))).
                thenReturn(nsr1Return);

        this.mockMvc.perform(post("/api/v1/ns-records").
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(gson.toJson(iperfNsd1))).
                andExpect(status().isCreated()).
                andDo(document("nsr-create-example", requestFields(
                        fieldWithPath("enabled").description("_"),
                        fieldWithPath("hb_version").description("_"),
                        fieldWithPath("name").description("The name of the NSR"),
                        fieldWithPath("vendor").type(JsonFieldType.STRING).description("The vendor of the NSR"),
                        fieldWithPath("version").description("The version of the NSR"),
                        fieldWithPath("vnf_dependency").description("An array of VNFDependencies"),
                        fieldWithPath("vnfd").description("Array of the <<resources-VirtualNetworkFunctionDescriptor, VNFDs>> of the NSR"),
                        fieldWithPath("vld").description("An array of VirtualLinkDescriptors")

                ))).
                andDo(document("nsr-create-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }



    @Test
    public void nsrCreateWithIdExample() throws Exception, QuotaExceededException, BadFormatException {
        when(networkServiceRecordManagement.onboard("c60ec37a-654a-4605-93fb-5a1932db6ecf")).
                thenReturn(nsr1Return);

        this.mockMvc.perform(post("/api/v1/ns-records/c60ec37a-654a-4605-93fb-5a1932db6ecf")).
                andExpect(status().isCreated());
    }



    @Test
    public void nsrGetAllExample() throws Exception {
        when(networkServiceRecordManagement.query()).thenReturn(nsrSetReturn);

        this.mockMvc.perform(get("/api/v1/ns-records/")).
                andExpect(status().isOk()).
                andDo(document("nsr-get-all-example", responseFields(
                        fieldWithPath("[]").description("The array of NSRs")
                ))).
                andDo(document("nsr-get-all-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }



    @Test
    public void nsrGetExample() throws Exception {
        when(networkServiceRecordManagement.query("66046d77-aade-4f14-ad39-f2976532e5f2")).thenReturn(nsr1Return);

        this.mockMvc.perform(get("/api/v1/ns-records/66046d77-aade-4f14-ad39-f2976532e5f2")).
                andExpect(status().isOk()).
                andDo(document("nsr-get-example", responseFields(
                        fieldWithPath("audit_log").type(JsonFieldType.STRING).description("_"),
                        fieldWithPath("auto_scale_policy").description("_"),
                        fieldWithPath("connection_point").description("_"),
                        fieldWithPath("descriptor_reference").type(JsonFieldType.STRING).description("The reference to the Network Service Descriptor used to instantiate this Network Service."),
                        fieldWithPath("faultManagementPolicy").type(JsonFieldType.STRING).description("Used for fault management"),
                        fieldWithPath("id").description("The id of the NSR"),
                        fieldWithPath("lifecycle_event").type(JsonFieldType.ARRAY).description("_"),
                        fieldWithPath("lifecycle_event_history").type(JsonFieldType.OBJECT).description("_"),
                        fieldWithPath("monitoring_parameter").type(JsonFieldType.ARRAY).description("Used for fault management"),
                        fieldWithPath("name").description("The name of the NSR"),
                        fieldWithPath("notification").type(JsonFieldType.STRING).description("_"),
                        fieldWithPath("pnfr").description("_"),
                        fieldWithPath("resource_reservation").type(JsonFieldType.STRING).description("_"),
                        fieldWithPath("runtime_policy_info").type(JsonFieldType.STRING).description("_"),
                        fieldWithPath("service_deployment_flavour").type(JsonFieldType.OBJECT).description("_"),
                        fieldWithPath("status").type(JsonFieldType.OBJECT).description("The status of the NSR"),
                        fieldWithPath("vendor").type(JsonFieldType.STRING).description("The vendor of the NSR"),
                        fieldWithPath("version").description("The version of the NSR"),
                        fieldWithPath("vlr").type(JsonFieldType.ARRAY).description("Array of VirtualLinkRecords that record resources used to implement a virtual link"),
                        fieldWithPath("vnf_dependency").type(JsonFieldType.ARRAY).description("Array of <<components-VNFRecordDependency, VNFRecordDependencies>>"),
                        fieldWithPath("vnffgr").description("_"),
                        fieldWithPath("vnfr").type(JsonFieldType.ARRAY).description("An array of <<resources-VirtualNetworkFunctionRecord, VirtualNetworkFunctionRecords>>")
                ))).
                andDo(document("nsr-get-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }



    @Test
    public void nsrUpdateExample() throws Exception {
        when(networkServiceRecordManagement.update(any(NetworkServiceRecord.class), eq("66046d77-aade-4f14-ad39-f2976532e5f2"))).
                thenReturn(nsr1Return);

        this.mockMvc.perform(put("/api/v1/ns-records/66046d77-aade-4f14-ad39-f2976532e5f2").
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(gson.toJson(nsr1))).
                andExpect(status().isAccepted()).
                andDo(document("nsr-update-example", requestFields(
                        fieldWithPath("descriptor_reference").type(JsonFieldType.STRING).description("The reference to the Network Service Descriptor used to instantiate this Network Service."),
                        fieldWithPath("name").description("The name of the NSR"),
                        fieldWithPath("status").description("The status of the NSR"),
                        fieldWithPath("vendor").type(JsonFieldType.STRING).description("The vendor of the NSR"),
                        fieldWithPath("version").description("The version of the NSR"),
                        fieldWithPath("vlr").description("A record of the resources used to implement a virtual network link"),
                        fieldWithPath("vnf_dependency").description("An array of <<components-VNFRecordDependency, VNFRecordDependencies>>"),
                        fieldWithPath("vnfr").description("An array of <<resources-VirtualNetworkFunctionRecord, VirtualNetworkFunctionRecords>>")

                ))).
                andDo(document("nsr-update-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }


    @Test
    public void nsrDeleteExample() throws Exception {
        Mockito.doNothing().when(networkServiceRecordManagement).delete("66046d77-aade-4f14-ad39-f2976532e5f2");

        this.mockMvc.perform(delete("/api/v1/ns-records/66046d77-aade-4f14-ad39-f2976532e5f2"))
                .andExpect(status().isNoContent());
    }



    @Test
    public void nsrGetAllVnfrExample() throws Exception {
        when(networkServiceRecordManagement.query("5e074545-0a81-4de8-8494-eb67173ec565")).thenReturn(nsr1Return);

        this.mockMvc.perform(get("/api/v1/ns-records/5e074545-0a81-4de8-8494-eb67173ec565/vnfrecords/")).
                andExpect(status().isOk()).
                andDo(document("nsr-get-all-vnfr-example", responseFields(
                        fieldWithPath("[]").description("The array of NSRs")
                ))).
                andDo(document("nsr-get-all-vnfr-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }



    @Test
    public void nsrGetVnfrExample() throws Exception {
        when(networkServiceRecordManagement.getVirtualNetworkFunctionRecord("5e074545-0a81-4de8-8494-eb67173ec565",
                "2135f315-1772-4ad8-85c8-caa209400ef0")).thenReturn(vnfrReturn);

        this.mockMvc.perform(get("/api/v1/ns-records/5e074545-0a81-4de8-8494-eb67173ec565/vnfrecords/2135f315-1772-4ad8-85c8-caa209400ef0")).
                andExpect(status().isOk()).
                andDo(document("nsr-get-vnfr-example", responseFields(
                        fieldWithPath("audit_log").type(JsonFieldType.STRING).description("_"),
                        fieldWithPath("auto_scale_policy").description("_"),
                        fieldWithPath("configurations").type(JsonFieldType.OBJECT).description("Configuration object to provide parameters to the Network Service"),
                        fieldWithPath("connected_external_virtual_link").type(JsonFieldType.ARRAY).description("_"),
                        fieldWithPath("connection_point").description("_"),
                        fieldWithPath("descriptor_reference").type(JsonFieldType.STRING).description("The reference to the <<resources-VirtualNetworkFunctionDescriptor, VNFD>> used to instantiate this VNF"),
                        fieldWithPath("deployment_flavour_key").type(JsonFieldType.STRING).description("The key representing the deployment flavour"),
                        fieldWithPath("endpoint").type(JsonFieldType.STRING).description("The type of the Vnfm in charge of managing this VNF"),
                        fieldWithPath("hb_version").description("_"),
                        fieldWithPath("id").description("The id of the VNFR"),
                        fieldWithPath("lifecycle_event").description("The lifecycle events that should be executed on the VNF triggered by the vnfm"),
                        fieldWithPath("lifecycle_event_history").type(JsonFieldType.ARRAY).description("_"),
                        fieldWithPath("localization").type(JsonFieldType.STRING).description("_"),
                        fieldWithPath("monitoring_parameter").type(JsonFieldType.ARRAY).description("Used for fault management"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("The name of the VNFR"),
                        fieldWithPath("notification").type(JsonFieldType.STRING).description("_"),
                        fieldWithPath("parent_ns_id").type(JsonFieldType.STRING).description("Reference to the <<resources-NetworkServiceRecord, NSRs>> that this VNFR is part of"),
                        fieldWithPath("packageId").type(JsonFieldType.STRING).description("The ID of the VNFPackage used to deploy this VNFR"),
                        fieldWithPath("provides").type(JsonFieldType.OBJECT).description("_"),
                        fieldWithPath("requires").type(JsonFieldType.OBJECT).description("_"),
                        fieldWithPath("runtime_policy_info").type(JsonFieldType.STRING).description("_"),
                        fieldWithPath("status").type(JsonFieldType.OBJECT).description("The status of the VNFR"),
                        fieldWithPath("task").type(JsonFieldType.STRING).description("_"),
                        fieldWithPath("type").type(JsonFieldType.STRING).description("The type of the VNFR"),
                        fieldWithPath("vdu").type(JsonFieldType.ARRAY).description("An aray of <<components-VirtualDeploymentUnit, VirtualDeploymentUnits>>"),
                        fieldWithPath("vendor").type(JsonFieldType.STRING).description("The vendor of the VNFR"),
                        fieldWithPath("version").type(JsonFieldType.STRING).description("The version of the VNFR"),
                        fieldWithPath("virtual_link").type(JsonFieldType.ARRAY).description("Internal Virtual Link instances used in this VNF"),
                        fieldWithPath("vnf_address").type(JsonFieldType.ARRAY).description("An array of addresses (e.g. VLAN, IP) configured for the management access or other internal and external connection interface on this VNF"),
                        fieldWithPath("vnfm_id").type(JsonFieldType.STRING).description("The identification of the <<components-VirtualNetworkFunctionManager, VNFM>> entity managing this VNF")
                ))).
                andDo(document("nsr-get-vnfr-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }



    @Test
    public void nsrDeleteVnfrExample() throws Exception {
        Mockito.doNothing().when(networkServiceRecordManagement).deleteVNFRecord("5e074545-0a81-4de8-8494-eb67173ec565", "2135f315-1772-4ad8-85c8-caa209400ef0");

        this.mockMvc.perform(delete("/api/v1/ns-records/5e074545-0a81-4de8-8494-eb67173ec565/vnfrecords/2135f315-1772-4ad8-85c8-caa209400ef0")).
                andExpect(status().isNoContent());
    }




    @Test
    public void vimInstanceCreateExample() throws Exception {
        when(vimManagement.add(any(VimInstance.class))).thenReturn(vimInstance0Return);

        this.mockMvc.perform(post("/api/v1/datacenters").
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(gson.toJson(vimInstance0))).
                andExpect(status().isCreated()).
                andDo(document("vim-instance-create-example", requestFields(
                        fieldWithPath("authUrl").description("The key authorisation URL of this PoP"),
                        fieldWithPath("location").description("The location of the data Center"),
                        fieldWithPath("name").description("The name of the VimInstance"),
                        fieldWithPath("password").description("The password"),
                        fieldWithPath("tenant").description("The tenant is a string to refer to a group of users"),
                        fieldWithPath("type").description("The type of the Vim Instance that will start the corresponding plugin"),
                        fieldWithPath("username").description("The user"),
                        fieldWithPath("version").description("The version of the VimInstance")
                ))).
                andDo(document("vim-instance-create-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }



    @Test
    public void vimInstanceGetAllExample() throws Exception {
        when(vimManagement.query()).thenReturn(vimInstanceSet);

        this.mockMvc.perform(get("/api/v1/datacenters")).
                andExpect(status().isOk()).
                andDo(document("vim-instance-get-all-example", responseFields(
                        fieldWithPath("[]").description("The array of VimInstances")
                ))).
                andDo(document("vim-instance-get-all-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }



    @Test
    public void vimInstanceGetExample() throws Exception {
        when(vimManagement.query("07241c3b-9d50-44ba-a495-9d3b96c226bd")).thenReturn(vimInstance0Return);

        this.mockMvc.perform(get("/api/v1/datacenters/07241c3b-9d50-44ba-a495-9d3b96c226bd")).
                andExpect(status().isOk()).
                andDo(document("vim-instance-get-example", responseFields(
                        fieldWithPath("authUrl").description("The key authorisation URL of this PoP"),
                        fieldWithPath("flavours").type(JsonFieldType.ARRAY).description("_"),
                        fieldWithPath("id").description("The id of the VimInstance"),
                        fieldWithPath("images").description("_"),
                        fieldWithPath("keyPair").type(JsonFieldType.STRING).description("The keyPair name stored into OpenStack to get the access to the VMs"),
                        fieldWithPath("location").type(JsonFieldType.OBJECT).description("The location of the data Center"),
                        fieldWithPath("name").description("The name of the VimInstance"),
                        fieldWithPath("networks").type(JsonFieldType.ARRAY).description("_"),
                        fieldWithPath("password").description("The password"),
                        fieldWithPath("securityGroups").type(JsonFieldType.ARRAY).description("_"),
                        fieldWithPath("tenant").description("The tenant is a string to refer to a group of users"),
                        fieldWithPath("type").description("The type of the Vim Instance that will start the corresponding plugin"),
                        fieldWithPath("username").description("The username"),
                        fieldWithPath("version").description("The version of the VimInstance")
                ))).
                andDo(document("vim-instance-get-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }



    @Test
    public void vimInstanceUpdateExample() throws Exception {
        when(vimManagement.update(any(VimInstance.class), eq("07241c3b-9d50-44ba-a495-9d3b96c226bd"))).
                thenReturn(vimInstance1Return);

        this.mockMvc.perform(put("/api/v1/datacenters/07241c3b-9d50-44ba-a495-9d3b96c226bd").
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(gson.toJson(vimInstance1))).
                andExpect(status().isAccepted()).
                andDo(document("vim-instance-update-example", requestFields(
                        fieldWithPath("authUrl").description("The key authorisation URL of this PoP"),
                        fieldWithPath("location").type(JsonFieldType.OBJECT).description("The location of the data Center"),
                        fieldWithPath("name").description("The name of the VimInstance"),
                        fieldWithPath("password").description("The password"),
                        fieldWithPath("tenant").description("The tenant is a string to refer to a group of users"),
                        fieldWithPath("type").description("The type of the Vim Instance that will start the corresponding plugin"),
                        fieldWithPath("username").description("The username"),
                        fieldWithPath("version").description("The version of the VimInstance")
                ))).
                andDo(document("vim-instance-update-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }


    @Test
    public void vimInstanceDeleteExample() throws Exception {
        Mockito.doNothing().when(vimManagement).delete("07241c3b-9d50-44ba-a495-9d3b96c226bd");

        this.mockMvc.perform(delete("/api/v1/datacenters/07241c3b-9d50-44ba-a495-9d3b96c226bd")).
                andExpect(status().isNoContent());
    }


    @Test
    public void vnfpackageGetAllExample() throws Exception {
        when(vnfPackageManagement.query()).thenReturn(vnfPackageSet);

        this.mockMvc.perform(get("/api/v1/vnf-packages")).
                andExpect(status().isOk()).
                andDo(document("vnfpackage-get-all-example", responseFields(
                        fieldWithPath("[]").description("The Array containing the VNFPackages")
                ))).
                andDo(document("vnfpackage-get-all-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }


    @Test
    public void vnfpackageGetExample() throws Exception {
        when(vnfPackageManagement.query("61a40e7c-a424-4d47-8413-532217ddcf4f")).thenReturn(vnfPackage1Return);

        this.mockMvc.perform(get("/api/v1/vnf-packages/61a40e7c-a424-4d47-8413-532217ddcf4f")).
                andExpect(status().isOk()).
                andDo(document("vnfpackage-get-example", responseFields(
                        fieldWithPath("id").description("The id of the VNFPackage"),
                        fieldWithPath("image").type(JsonFieldType.OBJECT).description("The image in the VNFPackage used for instantiating the VMs"),
                        fieldWithPath("imageLink").type(JsonFieldType.STRING).description("A link to an image that shall be used for instantiating the VMs"),
                        fieldWithPath("name").description("The name of the VNFPackage"),
                        fieldWithPath("scripts").type(JsonFieldType.ARRAY).description("The scripts that are stored in the VNFPackage"),
                        fieldWithPath("scriptsLink").type(JsonFieldType.STRING).description("A link to a git repository, from which the script files can be downloaded"),
                        fieldWithPath("version").description("The version of the VNFPackage")
                ))).
                andDo(document("vnfpackage-get-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }



    @Test
    public void vnfpackageUpdateExample() throws Exception {
        when(vnfPackageManagement.update(eq("61a40e7c-a424-4d47-8413-532217ddcf4f"), any(VNFPackage.class))).
                thenReturn(vnfPackage1Return);

        this.mockMvc.perform(put("/api/v1/vnf-packages/61a40e7c-a424-4d47-8413-532217ddcf4f").
                contentType(MediaType.APPLICATION_JSON_VALUE).
                content(gson.toJson(vnfPackage1))).
                andExpect(status().isAccepted()).
                andDo(document("vnfpackage-update-example", requestFields(
                        fieldWithPath("image").description("The image in the VNFPackage used for instantiating the VMs"),
                        fieldWithPath("imageLink").description("A link to an image that shall be used for instantiating the VMs"),
                        fieldWithPath("name").description("The name of the VNFPackage"),
                        fieldWithPath("scriptsLink").description("A link to a git repository, from which the script files can be downloaded"),
                        fieldWithPath("version").description("The version of the VNFPackage")
                ))).
                andDo(document("vnfpackage-update-example", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }


    @Test
    public void vnfpackageDeleteExample() throws Exception {
        Mockito.doNothing().when(vnfPackageManagement).delete("61a40e7c-a424-4d47-8413-532217ddcf4f");

        this.mockMvc.perform(delete("/api/v1/vnf-packages/61a40e7c-a424-4d47-8413-532217ddcf4f")).
                andExpect(status().isNoContent());
    }


}