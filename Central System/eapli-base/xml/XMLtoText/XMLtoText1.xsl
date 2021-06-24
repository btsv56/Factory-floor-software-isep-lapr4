<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : XMLtoText.xsl
    Created on : 5 de Junho de 2020, 11:20
    Author     : Utilizador
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="text"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/SSFM">
        SSFM
        <xsl:apply-templates select="RawMaterialCategories"/>
        <xsl:apply-templates select="RawMaterials"/>
        <xsl:apply-templates select="Products"/>
        <xsl:apply-templates select="Deposits"/>
        <xsl:apply-templates select="ProductionSheets"/>
        <xsl:apply-templates select="Lots"/>
        <xsl:apply-templates select="ProductionOrders"/>
        <xsl:apply-templates select="ProductionLines"/>
        <xsl:apply-templates select="Consumptions"/>
        <xsl:apply-templates select="Machines" />
        <xsl:apply-templates select="Wastes" />
        <xsl:apply-templates select="EffectiveTimes" />
        <xsl:apply-templates select="BruteTimes" />
    </xsl:template>
    
    <!-- Raw Material Categories -->
    <xsl:template match="RawMaterialCategories">


        Raw Material Categories:<xsl:apply-templates  select="RawMaterialCategory"/>
    </xsl:template>
   
    <xsl:template match="RawMaterialCategory">
        
        Name: <xsl:value-of select="@name"/>
        Description: <xsl:value-of select="description"/>
    </xsl:template>
    
    <!-- Raw Materials -->
    <xsl:template match="RawMaterials">


        Raw Materials: <xsl:apply-templates  select="RawMaterial"/>
    </xsl:template>
   
    <xsl:template match="RawMaterial">

        Internal Code: <xsl:value-of select="@internalCode"/>
        Category: <xsl:value-of select="@nameRawMaterialCategory"/>
        Description: <xsl:value-of select="description"/>
        Technical Sheet: <xsl:value-of select="TechnicalSheet/nameTechnicalSheet"/>
    </xsl:template>

    <!-- Products -->
    <xsl:template match="Products">


        Products: <xsl:apply-templates select="Product"/>
    </xsl:template>

    <xsl:template match="Product">

        Fabrication Code: <xsl:value-of select="@fabricationCode"/>
        Comercial Code: <xsl:value-of select="comercialCode"/>
        Brief Description: <xsl:value-of select="briefDescription"/>
        Complete Description: <xsl:value-of select="completeDescription"/>
        Product Category: <xsl:value-of select="productCategory"/>
        Unity: <xsl:value-of select="unity"/>
        Production Sheet: <xsl:value-of select="productionSheet"/>
    </xsl:template>

    <!-- Deposits -->
    <xsl:template match="Deposits">


        Deposits: <xsl:apply-templates select="Deposit"/>
    </xsl:template>

    <xsl:template match="Deposit">

        Internal Code: <xsl:value-of select="@internalCode"/>
        Description: <xsl:value-of select="Description"/>
            Raw Materials:<xsl:for-each select="RawMaterials/RawMaterial">
                - <xsl:value-of select="@ID"/> (<xsl:value-of select="@Quantity"/>)</xsl:for-each>
            Products:<xsl:for-each select="Products/Product">
                - <xsl:value-of select="@ID"/> (<xsl:value-of select="@Quantity"/>)</xsl:for-each>
    </xsl:template>

    <!-- Production Sheets -->
    <xsl:template match="Productionsheets">


        Products: <xsl:apply-templates select="ProductionSheet"/>
    </xsl:template>

    <xsl:template match="ProductionSheet">

        Production Sheet ID: <xsl:value-of select="@ID"/>
        <xsl:apply-templates select="ProductionSheetLineProduct"/>
        <xsl:apply-templates select="ProductionSheetLineRawMaterial"/>
    </xsl:template>

    <xsl:template match="ProductionSheetLineProduct">
        <xsl:for-each select="Product">
            Product ID: <xsl:value-of select="@ID"/>
            Quantity: <xsl:value-of select="@Quantity"/>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="ProductionSheetLineRawMaterial">
        <xsl:for-each select="RawMaterial">
            Raw Material: <xsl:value-of select="@ID"/>
            Quantity: <xsl:value-of select="@Quantity"/>
        </xsl:for-each>
    </xsl:template>

    <!-- Lots -->
    <xsl:template match="Lots">


        Lots: <xsl:apply-templates select="Lot"/>
    </xsl:template>

    <xsl:template match="Lot">

        Internal Code: <xsl:value-of select="@internalCode"/>
    </xsl:template>

    <!-- Production Orders -->
    <xsl:template match="ProductionOrders">


        Production Orders: <xsl:apply-templates select="ProductionOrder"/>
    </xsl:template>

    <xsl:template match="ProductionOrder">

        Internal Code: <xsl:value-of select="@internalCode"/>
        Description: <xsl:value-of select="Description"/>
        State: <xsl:value-of select="State"/>
        Lot: <xsl:value-of select="Lot/@ID"/>
        Request: <xsl:value-of select="Request/@ID"/>
        Emission Date: <xsl:value-of select="EmissionDate"/>
        Predicted Execution Date: <xsl:value-of select="PredictedExecutionDate"/>
        Production Sheet: <xsl:value-of select="ProductionSheet/@ID"/>
        Quantity To Produce: <xsl:value-of select="QuantityToProduce"/>
    </xsl:template>

    <!-- Production Lines -->
    <xsl:template match="ProductionLines">


        Production Lines: <xsl:apply-templates select="ProductionLine"/>
    </xsl:template>

    <xsl:template match="ProductionLine">

        Internal Code: <xsl:value-of select="@internalCode"/>
        Description: <xsl:value-of select="Description"/>
            Machines:<xsl:for-each select="Machines/Machine">
                - <xsl:value-of select="@ID"/>
        </xsl:for-each>
    </xsl:template>
    
    <!-- Consumptions -->
    <xsl:template match="Consumptions">


        Consumptions:
        <xsl:apply-templates select="EffectiveConsumptions"/>
        <xsl:apply-templates select="RealConsumptions"/>
    </xsl:template>
    
    <!-- Effective Consumptions -->
    <xsl:template match="EffectiveConsumptions">
        Effective Consumptions:<xsl:apply-templates select="Consumption"/>
    </xsl:template>
    
    <xsl:template match="//EffectiveConsumptions/Consumption">
        
        Machine: <xsl:value-of select="@machine"/>
        Deposit: <xsl:value-of select="@deposit"/>
        RawMaterial: <xsl:value-of select="@rawMaterial"/>
        <xsl:value-of select="@product"/>
        Quantity: <xsl:value-of select="quantity"/>
    </xsl:template>
    
    <!-- Real Consumptions -->
    <xsl:template match="RealConsumptions">
        
        RealConsumptions:<xsl:apply-templates select="Deposit"/>
        <xsl:apply-templates select="RawMaterial"/>
        <xsl:apply-templates select="Product"/>
    </xsl:template>
    
    <xsl:template match="//RealConsumptions/Deposit">
        
        Deposit :<xsl:value-of select="@id"/>
        Quantity: <xsl:value-of select="quantity"/>
    </xsl:template>

    <xsl:template match="//RealConsumptions/RawMaterial">
        
        RawMaterial: <xsl:value-of select="@id"/>"
        Quantity: <xsl:value-of select="quantity"/>"
    </xsl:template>
    
    <xsl:template match="//RealConsumptions/Product">
        
        Product: <xsl:value-of select="@id"/>"
        Quantity: <xsl:value-of select="quantity"/>"
    </xsl:template>

    <xsl:template match="Machines">

        Machines: <xsl:apply-templates select="Machine" />

        Configuration Files: <xsl:apply-templates select="Machine/ConfigurationFiles/ConfigurationFile" />
    </xsl:template>

    <xsl:template match="Machine">

            Internal Code: <xsl:value-of select="@InternalCode" />
            State: <xsl:value-of select="@State" />
            Serial Number: <xsl:value-of select="SerialNumber" />
            Description: <xsl:value-of select="Description" />
            Installation Date: <xsl:value-of select="InstallationDate" />
            Brand: <xsl:value-of select="Brand" />
            Model: <xsl:value-of select="Model" />
            <xsl:if test="Protocol">
            Protocol: <xsl:value-of select="Protocol/@ID" />
            </xsl:if>
            <xsl:if test="Machine">
            Followed by Machine: <xsl:value-of select="Machine/@InternalCode" />
            </xsl:if>

    </xsl:template>


    <xsl:template match="Machine/ConfigurationFiles/ConfigurationFile">

            Machine: <xsl:value-of select="../../@InternalCode" />
            Description: <xsl:value-of select="." />
    </xsl:template>

    <xsl:template match="Wastes">

        Wastes:<xsl:apply-templates select="Waste" />
    </xsl:template>

    <xsl:template match="Waste">

            Production Order: <xsl:value-of select="./ProductionOrder/@ID" />
            Machine: <xsl:value-of select="./Machine/@InternalCode" />
            <xsl:if test="./Product">
            Product: <xsl:value-of select="./Product/@ID" /></xsl:if>
            <xsl:if test="./RawMaterial">
            Raw Material: <xsl:value-of select="./RawMaterial/@ID" /></xsl:if>
            Quantity: <xsl:value-of select="@quantity" />
            Deposit: <xsl:value-of select="./Deposit/@ID" />
    </xsl:template>

    <xsl:template match="EffectiveTimes">

        Effective Times: <xsl:apply-templates select="EffectiveTime" />
    </xsl:template>

    <xsl:template match="EffectiveTime">

            Production Order: <xsl:value-of select="./ProductionOrder/@ID" />
            Machine: <xsl:value-of select="./Machine/@InternalCode" />
            Minutes: <xsl:value-of select="./@Minutes" />
            Seconds: <xsl:value-of select="./@Seconds" />
    </xsl:template>

    <xsl:template match="BruteTimes">

        Brute Times: <xsl:apply-templates select="BruteTime" />
    </xsl:template>

    <xsl:template match="BruteTime">

            Production Order: <xsl:value-of select="./ProductionOrder/@ID" />
            Machine: <xsl:value-of select="./Machine/@InternalCode" />
            Minutes: <xsl:value-of select="./@Minutes" />
            Seconds: <xsl:value-of select="./@Seconds" />
    </xsl:template>


</xsl:stylesheet>
