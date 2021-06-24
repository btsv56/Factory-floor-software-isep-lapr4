<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : XMLtoJson.xsl
    Created on : 4 de Junho de 2020, 16:48
    Author     : Utilizador
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="text"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/SSFM">
        {
            "SSFM" : {
                <xsl:apply-templates select="RawMaterialCategories"/>
                <xsl:apply-templates select="RawMaterials"/>
                <xsl:apply-templates select="Products"/>
                <xsl:apply-templates select="Deposits"/>
                <xsl:apply-templates select="ProductionSheets"/>
                <xsl:apply-templates select="Lots"/>
                <xsl:apply-templates select="ProductionOrders"/>
                <xsl:apply-templates select="ProductionLines"/>
                <xsl:apply-templates select="Machines"/>
                <xsl:call-template name="ConfigurationFiles"/>
            }
        }
    </xsl:template>

    <!-- Raw Material Categories -->
    <xsl:template match="RawMaterialCategories">
        "Raw Material Categories" : { "RawMaterialCategory" :[ 
        <xsl:apply-templates  select="RawMaterialCategory"/>
        ]},
    </xsl:template>
   
    <xsl:template match="RawMaterialCategory">
        {
        "name" : "<xsl:value-of select="@name"/>",
        "description" : "<xsl:value-of select="description"/>"
        }<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>
    
    <!-- Raw Materials -->
    <xsl:template match="RawMaterials">
        "Raw Materials" : { "RawMaterial" :[ 
        <xsl:apply-templates  select="RawMaterial"/>
        ]}<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>
   
    <xsl:template match="RawMaterial">
        {
        "internalCode" : "<xsl:value-of select="@internalCode"/>",
        "nameRawMaterial" : "<xsl:value-of select="@nameRawMaterialCategory"/>",
        "description" : "<xsl:value-of select="description"/>",
        "TechnicalSheet" : {
         
        "nameTechnicalSheet" : "<xsl:value-of select="TechnicalSheet/nameTechnicalSheet"/>"
        }
        }<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <!-- Products -->
    <xsl:template match="Products">
        "Products" : { "Product" :[
        <xsl:apply-templates select="Product"/>
        ]}
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="Product">
        {
        "fabricationCode" : "<xsl:value-of select="@fabricationCode"/>",
        "comercialCode" : "<xsl:value-of select="comercialCode"/>",
        "briefDescription" : "<xsl:value-of select="briefDescription"/>",
        "completeDescription" : "<xsl:value-of select="completeDescription"/>",
        "productCategory" : "<xsl:value-of select="productCategory"/>",
        "unity" : "<xsl:value-of select="unity"/>",
        "productionSheet" : "<xsl:value-of select="productionSheet"/>"}
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <!-- Deposits -->
    <xsl:template match="Deposits">
        "Deposits" : { "Deposit" :[
        <xsl:apply-templates select="Deposit"/>
        ]}<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="Deposit">
        {
        "internalCode" : "<xsl:value-of select="@internalCode"/>",
        "Description" : "<xsl:value-of select="Description"/>",
        <xsl:for-each select="RawMaterials/RawMaterial">
            "rawMaterialID<xsl:value-of select="position()"/>" : "<xsl:value-of select="@ID"/>",
            "RMQuantity<xsl:value-of select="position()"/>" : "<xsl:value-of select="@Quantity"/>"
            <xsl:if test="../following-sibling::*">,</xsl:if>
        </xsl:for-each>
        <xsl:for-each select="Products/Product">
            "productID<xsl:value-of select="position()"/>" : "<xsl:value-of select="@ID"/>",
            "PQuantity<xsl:value-of select="position()"/>" : "<xsl:value-of select="@Quantity"/>"
            <xsl:if test="following-sibling::*">,</xsl:if>
        </xsl:for-each>}
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <!-- Production Sheets -->
    <xsl:template match="ProductionSheets">
        "Production Sheets" : { "Production Sheet" :[
        <xsl:apply-templates select="ProductionSheet"/>
        ]}
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="ProductionSheet">
        {
        "productionSheetID" : "<xsl:value-of select="@ID"/>",
        <xsl:apply-templates select="ProductionSheetLineProduct"/>
        <xsl:apply-templates select="ProductionSheetLineRawMaterial"/>}
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="ProductionSheetLineProduct">
        <xsl:for-each select="Product">
            "productID<xsl:value-of select="position()"/>" : "<xsl:value-of select="@ID"/>",
            "PQuantity<xsl:value-of select="position()"/>" : "<xsl:value-of select="@Quantity"/>",
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="ProductionSheetLineRawMaterial">
        <xsl:for-each select="RawMaterial">
            "rawMaterialID<xsl:value-of select="position()"/>" : "<xsl:value-of select="@ID"/>",
            "RQuantity<xsl:value-of select="position()"/>" : "<xsl:value-of select="@Quantity"/>"
            <xsl:if test="following-sibling::*">,</xsl:if>
        </xsl:for-each>
    </xsl:template>

    <!-- Lots -->
    <xsl:template match="Lots">
        "Lots" : { "Lot" :[
        <xsl:apply-templates select="Lot"/>
        ]}
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="Lot">
        {
        "lotID" : "<xsl:value-of select="@internalCode"/>"}
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <!-- Production Orders -->
    <xsl:template match="ProductionOrders">
        "Production Orders" : { "Production Order" :[
        <xsl:apply-templates select="ProductionOrder"/>
        ]}
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="ProductionOrder">
        {
        "internalCode" : "<xsl:value-of select="@internalCode"/>",
        "description" : "<xsl:value-of select="Description"/>",
        "state" : "<xsl:value-of select="State"/>",
        "lot" : "<xsl:value-of select="Lot/@ID"/>",
        "request" : "<xsl:value-of select="Request/@ID"/>",
        "emissionDate" : "<xsl:value-of select="EmissionDate"/>",
        "predictedExecutionDate" : "<xsl:value-of select="PredictedExecutionDate"/>",
        "productionSheet" : "<xsl:value-of select="ProductionSheet/@ID"/>",
        "quantityToProduce" : "<xsl:value-of select="QuantityToProduce"/>"}
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <!-- Production Lines -->
    <xsl:template match="ProductionLines">
        "Production Lines" : { "Production Line" :[
        <xsl:apply-templates select="ProductionLine"/>
        ]}
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="ProductionLine">
        {
        "internalCode" : "<xsl:value-of select="@internalCode"/>",
        "description" : "<xsl:value-of select="Description"/>",
        <xsl:for-each select="Machines/Machine">
            "machineID<xsl:value-of select="position()"/>" : "<xsl:value-of select="@ID"/>"
            <xsl:if test="following-sibling::*">,</xsl:if>
        </xsl:for-each>
        }
        <xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <!-- Machines -->
    <xsl:template match="Machines">
        "Machines" :[<xsl:apply-templates select="Machine" />
        ]<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="Machine">
            {
                "InternalCode" : "<xsl:value-of select="@InternalCode" />",
                "State" : "<xsl:value-of select="@State" />",
                "SerialNumber" : "<xsl:value-of select="SerialNumber" />",
                "Description" : "<xsl:value-of select="Description" />",
                "InstallationDate" : "<xsl:value-of select="InstallationDate" />",
                "Brand" : "<xsl:value-of select="Brand" />",
                "Model" : "<xsl:value-of select="Model" />",
                "Protocol" : "<xsl:value-of select="Protocol/@ID" />",
                "Machine" : "<xsl:value-of select="Machine/@InternalCode" />"
            }<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <!-- Configuration Files -->

    <xsl:template name="ConfigurationFiles">
        "ConfigurationFiles" :[<xsl:call-template name="ConfigurationFilePerMachine" />
        ]<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template name="ConfigurationFilePerMachine">

        <xsl:for-each select="Machines/Machine">
            <xsl:variable name="mach">
                <xsl:value-of select="@InternalCode" />
            </xsl:variable>
            {
                "Machine" : "<xsl:value-of select="$mach" />",
                "ConfigurationFiles" : [<xsl:for-each select="ConfigurationFiles/ConfigurationFile">
                        "<xsl:value-of select="." />"<xsl:if test="following-sibling::*">,</xsl:if>
                    </xsl:for-each>
                ]
            }<xsl:if test="following-sibling::*">,</xsl:if>
        </xsl:for-each><xsl:if test="following-sibling::*">,</xsl:if>

    </xsl:template>

</xsl:stylesheet>
