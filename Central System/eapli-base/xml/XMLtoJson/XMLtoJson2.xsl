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
            "Stock Management" : {
                <xsl:apply-templates select="Consumptions"/>
            },
            
            <xsl:apply-templates select="Wastes"/>,
            <xsl:apply-templates select="EffectiveTimes"/>,
            <xsl:apply-templates select="BruteTimes"/>
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
    
    <!-- Consumptions -->
    <xsl:template match="Consumptions">
        "Consumptions" : {
        <xsl:apply-templates select="EffectiveConsumptions"/>
        <xsl:apply-templates select="RealConsumptions"/>
        }
    </xsl:template>
    
    <!-- Effective Consumptions -->
    <xsl:template match="EffectiveConsumptions">
        "EffectiveConsumptions" : { "Consumption" : [
        <xsl:apply-templates select="Consumption"/>
        ]}<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>
    
    <xsl:template match="//EffectiveConsumptions/Consumption">
        {
        "machine" : "<xsl:value-of select="@machine"/>",
        "deposit" : "<xsl:value-of select="@deposit"/>",
        "rawMaterial" : "<xsl:value-of select="@rawMaterial"/><xsl:value-of select="@product"/>",
        "quantity" : "<xsl:value-of select="quantity"/>"
        }<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>
    
    <!-- Real Consumptions -->
    <xsl:template match="RealConsumptions">
        "RealConsumptions<xsl:value-of select="position()"/>" : {
        <xsl:apply-templates select="Deposit"/>
        <xsl:apply-templates select="RawMaterial"/>
        <xsl:apply-templates select="Product"/>
        }<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>
    
    <xsl:template match="//RealConsumptions/Deposit">
        "Deposit<xsl:value-of select="position()"/>" : {
        "deposit" : "<xsl:value-of select="@id"/>",
        "quantity": "<xsl:value-of select="quantity"/>"
        }<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <xsl:template match="//RealConsumptions/RawMaterial">
        "RawMaterial<xsl:value-of select="position()"/>" : {
        "rawMaterial" : "<xsl:value-of select="@id"/>",
        "quantity" : "<xsl:value-of select="quantity"/>"
        }<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>
    
    <xsl:template match="//RealConsumptions/Product">
        "Product<xsl:value-of select="position()"/>" : {
        "product" : "<xsl:value-of select="@id"/>",
        "quantity" : "<xsl:value-of select="quantity"/>"
        }<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <!-- Wastes -->
    <xsl:template match="Wastes">
        "Wastes" :[<xsl:apply-templates select="Waste" />
        ]
    </xsl:template>

    <xsl:template match="Waste">
            {
                "ProductionOrder" : "<xsl:value-of select="ProductionOrder/@ID" />",
                "Machine" : "<xsl:value-of select="Machine/@InternalCode" />",
                <xsl:if test="Product">"Product" : "<xsl:value-of select="Product/@ID" />",</xsl:if>
                <xsl:if test="RawMaterial">"RawMaterial" : "<xsl:value-of select="RawMaterial/@ID" />",</xsl:if>
                "Quantity" : "<xsl:value-of select="@quantity" />",
                "Deposit" : "<xsl:value-of select="Deposit/@ID" />"
            }<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <!-- EffecitveTimes -->
    <xsl:template match="EffectiveTimes">
        "EffectiveTimes" :[<xsl:apply-templates select="EffectiveTime" />
        ]
    </xsl:template>

    <xsl:template match="EffectiveTime">
            {
                "ProductionOrder" : "<xsl:value-of select="ProductionOrder/@ID" />",
                "Machine" : "<xsl:value-of select="Machine/@InternalCode" />",
                "Minutes" : "<xsl:value-of select="@Minutes" />",
                "Seconds" : "<xsl:value-of select="@Seconds" />"
            }<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    <!-- BruteTimes -->
    <xsl:template match="BruteTimes">
        "BruteTimes" :[<xsl:apply-templates select="BruteTime" />
        ]
    </xsl:template>

    <xsl:template match="BruteTime">
            {
                "ProductionOrder" : "<xsl:value-of select="ProductionOrder/@ID" />",
                "Machine" : "<xsl:value-of select="Machine/@InternalCode" />",
                "Minutes" : "<xsl:value-of select="@Minutes" />",
                "Seconds" : "<xsl:value-of select="@Seconds" />"
            }<xsl:if test="following-sibling::*">,</xsl:if>
    </xsl:template>

    
</xsl:stylesheet>
