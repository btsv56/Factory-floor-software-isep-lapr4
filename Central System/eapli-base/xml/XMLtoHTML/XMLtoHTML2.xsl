<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : XMLtoXHTML2.xsl
    Created on : 6 de Junho de 2020, 14:34
    Author     : Utilizador
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xhtml="http://www.w3.org/1999/xhtml" version="2.0">
    <xsl:output method="xhtml" omit-xml-declaration="yes" indent="yes" />

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/SSFM">
        <html>
            <head>
                <title>Factory floor</title>
            </head>
            <body>
                <h1>Factory floor</h1>
                <xsl:apply-templates select="RawMaterialCategories" />
                <xsl:apply-templates select="RawMaterials" />
                <xsl:apply-templates select="Products" />
                <xsl:apply-templates select="Deposits" />
                <xsl:apply-templates select="ProductionSheets" />
                <xsl:apply-templates select="Lots" />
                <xsl:apply-templates select="ProductionOrders" />
                <xsl:apply-templates select="ProductionLines" />
                <xsl:apply-templates select="Consumptions" />
                <xsl:apply-templates select="Machines" />
                <xsl:apply-templates select="Wastes" />
                <xsl:apply-templates select="EffectiveTimes" />
                <xsl:apply-templates select="BruteTimes" />
            </body>
        </html>
    </xsl:template>

    <!-- Raw Materials Categories-->
    <xsl:template match="RawMaterialCategories">
        <h2>Raw Material Categories</h2>
        <xsl:apply-templates select="RawMaterialCategory">
            <xsl:sort select="@name" data-type="text" order="ascending" />
        </xsl:apply-templates>
    </xsl:template>

    <xsl:template match="RawMaterialCategory">
        <p>
            Name:
            <xsl:value-of select="@name" />
            <br />
            Description:
            <xsl:value-of select="description" />
            <br />
        </p>
    </xsl:template>

    <!-- Raw Materials -->
    <xsl:template match="RawMaterials">
        <h2>Raw Materials</h2>
        <xsl:apply-templates select="RawMaterial">
            <xsl:sort select="@interalCode" data-type="text" order="ascending" />
        </xsl:apply-templates>
    </xsl:template>

    <xsl:template match="RawMaterial">
        <p>
            Internal Code:
            <xsl:value-of select="@internalCode" />
            <br />
            Category:
            <xsl:value-of select="@nameRawMaterialCategory" />
            <br />
            Description:
            <xsl:value-of select="description" />
            <br />
            Technical Sheet:
            <xsl:value-of select="TechnicalSheet/nameTechnicalSheet" />
            <br />
        </p>
    </xsl:template>

    <!-- Products -->
    <xsl:template match="Products">
        <h2>Products</h2>
        <xsl:apply-templates select="Product">
            <xsl:sort select="@fabricationCode" data-type="text" order="ascending" />
        </xsl:apply-templates>
    </xsl:template>

    <xsl:template match="Product">
        <p>
            FabricationCode:
            <xsl:value-of select="@fabricationCode" />
            <br />
            ComercialCode:
            <xsl:value-of select="comercialCode" />
            <br />
            Brief Description:
            <xsl:value-of select="briefDescription" />
            <br />
            Complete Description:
            <xsl:value-of select="completeDescription" />
            <br />
            Product Category:
            <xsl:value-of select="productCategory" />
            <br />
            Unity:
            <xsl:value-of select="unity" />
            <br />
            Production Sheet:
            <xsl:value-of select="productionSheet" />
            <br />
        </p>
    </xsl:template>

    <!-- Deposits -->
    <xsl:template match="Deposits">
        <h2>Deposits</h2>
        <xsl:apply-templates select="Deposit" />
    </xsl:template>

    <xsl:template match="Deposit">
        <p>
            Internal Code:
            <xsl:value-of select="@internalCode" />
            <br />
            Description:
            <xsl:value-of select="Description" />
            <br />
            Raw Materials:
            <br />
            <xsl:for-each select="RawMaterials/RawMaterial">
                <xsl:text>&#x3000;</xsl:text>
                -
                <xsl:value-of select="@ID" />
                (
                <xsl:value-of select="@Quantity" />
                )
                <br />
            </xsl:for-each>
            Products:
            <br />
            <xsl:for-each select="Products/Product">
                <xsl:text>&#x3000;</xsl:text>
                -
                <xsl:value-of select="@ID" />
                (
                <xsl:value-of select="@Quantity" />
                )
                <br />
            </xsl:for-each>
        </p>
    </xsl:template>

    <!-- Production Sheets> -->
    <xsl:template match="ProductionSheets">
        <h2>Production Sheets</h2>
        <xsl:apply-templates select="ProductionSheet">
            <xsl:sort select="@fabricationCode" data-type="text" order="ascending" />
        </xsl:apply-templates>
    </xsl:template>

    <xsl:template match="ProductionSheet">
        Production Sheet ID:
        <xsl:value-of select="@ID" />
        <br />
        Products:
        <br />
        <xsl:apply-templates select="ProductionSheetLineProduct" />
        RawMaterials:
        <br />
        <xsl:apply-templates select="ProductionSheetLineRawMaterial" />
        <br />
    </xsl:template>

    <xsl:template match="ProductionSheetLineProduct">
        <xsl:for-each select="Product">
            ID:
            <xsl:value-of select="@ID" />
            <br />
            Quantity:
            <xsl:value-of select="@Quantity" />
            <br />
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="ProductionSheetLineRawMaterial">
        <xsl:for-each select="RawMaterial">
            ID:
            <xsl:value-of select="@ID" />
            <br />
            Quantity:
            <xsl:value-of select="@Quantity" />
            <br />
        </xsl:for-each>
    </xsl:template>

    <!-- Lots -->
    <xsl:template match="Lots">
        <h2>Lots</h2>
        <xsl:apply-templates select="Lot" />
    </xsl:template>

    <xsl:template match="Lot">
        <p>
            Internal Code:
            <xsl:value-of select="@internalCode" />
            <br />
        </p>
    </xsl:template>

    <!-- Production Orders -->
    <xsl:template match="ProductionOrders">
        <h2>Production Orders</h2>
        <xsl:apply-templates select="ProductionOrder" />
    </xsl:template>

    <xsl:template match="ProductionOrder">
        <p>
            Internal Code:
            <xsl:value-of select="@internalCode" />
            <br />
            Description:
            <xsl:value-of select="Description" />
            <br />
            State:
            <xsl:value-of select="State" />
            <br />
            Lot:
            <xsl:value-of select="Lot/@ID" />
            <br />
            Request:
            <xsl:value-of select="Request/@ID" />
            <br />
            Emission Date:
            <xsl:value-of select="EmissionDate" />
            <br />
            Predicted Execution Date:
            <xsl:value-of select="PredictedExecutionDate" />
            <br />
            Production Sheet:
            <xsl:value-of select="ProductionSheet/@ID" />
            <br />
            Quantity To Produce:
            <xsl:value-of select="QuantityToProduce" />
            <br />
        </p>
    </xsl:template>

    <!-- Production Lines -->
    <xsl:template match="ProductionLines">
        <h2>Production Lines</h2>
        <xsl:apply-templates select="ProductionLine" />
    </xsl:template>

    <xsl:template match="ProductionLine">
        <p>
            Internal Code:
            <xsl:value-of select="@internalCode" />
            <br />
            Description:
            <xsl:value-of select="Description" />
            <br />
            Machines
            <br />
            <xsl:for-each select="Machines/Machine">
                <xsl:text>&#x3000;</xsl:text>
                -
                <xsl:value-of select="@ID" />
                <br />
            </xsl:for-each>
        </p>
    </xsl:template>

    <!-- Consumptions-->
    <xsl:template match="Consumptions">
        <h2>Consumptions</h2>
        <xsl:apply-templates select="EffectiveConsumptions" />
        <xsl:apply-templates select="RealConsumptions" />
    </xsl:template>

    <!-- Effective Consumptions -->
    <xsl:template match="EffectiveConsumptions">
        <h2>Effective Consumptions</h2>
        <xsl:apply-templates select="Consumption" />
    </xsl:template>

    <xsl:template match="Consumption">
        <p>
            Machine:
            <xsl:value-of select="@machine" />
            <br />
            <xsl:if test="@deposit">
                Deposit:
                <xsl:value-of select="@deposit" />
                <br />
            </xsl:if>
            Raw Material:
            <xsl:value-of select="@rawMaterial" />
            <xsl:value-of select="@product" />
            <br />
            Quantity:
            <xsl:value-of select="quantity" />
            <br />
        </p>

    </xsl:template>

    <!-- RealConsumptions -->
    <xsl:template match="RealConsumptions">
        <h2>Real Consumptions</h2>
        <xsl:call-template name="DepositConsumption" />
        <xsl:call-template name="RawMaterialConsumption" />
        <xsl:call-template name="ProductConsumption" />
    </xsl:template>

    <xsl:template name="RawMaterialConsumption">
        <h3>Raw Material Consumptions</h3>
        <xsl:apply-templates select="RawMaterial" />
    </xsl:template>

    <xsl:template match="//RealConsumptions/RawMaterial">
        <p>
            Raw Material:
            <xsl:value-of select="@id" />
            <br />
            Quantity:
            <xsl:value-of select="quantity" />
            <br />
        </p>
    </xsl:template>

    <xsl:template name="ProductConsumption">
        <h3>Product Consumptions</h3>
        <xsl:apply-templates select="Product" />
    </xsl:template>

    <xsl:template match="//RealConsumptions/Product">
        <p>
            Product:
            <xsl:value-of select="@id" />
            <br />
            Quantity:
            <xsl:value-of select="quantity" />
            <br />
        </p>
    </xsl:template>

    <xsl:template name="DepositConsumption">

        <h3>Deposit Consumptions</h3>

        <xsl:for-each select="Deposit">
            <p>
                Deposit:
                <xsl:value-of select="@id" />
                <br />
                Quantity:
                <xsl:value-of select="quantity" />
                <br />
            </p>
        </xsl:for-each>

    </xsl:template>

    <xsl:template match="Machines">
        <h3>Machines</h3>
        <xsl:apply-templates select="Machine" />
        <h3>Configuration Files</h3>
        <xsl:apply-templates select="Machine/ConfigurationFiles/ConfigurationFile" />
    </xsl:template>

    <xsl:template match="Machine">
        <p>
            Internal Code:
            <xsl:value-of select="@InternalCode" />
            <br />
            State:
            <xsl:value-of select="@State" />
            <br />
            Serial Number:
            <xsl:value-of select="SerialNumber" />
            <br />
            Description:
            <xsl:value-of select="Description" />
            <br />
            Installation Date:
            <xsl:value-of select="InstallationDate" />
            <br />
            Brand:
            <xsl:value-of select="Brand" />
            <br />
            Model:
            <xsl:value-of select="Model" />
            <br />
            <xsl:apply-templates select="Protocol" />
            <xsl:call-template name="MachineID" />
        </p>
    </xsl:template>

    <xsl:template match="Protocol">
        Protocol:
        <xsl:value-of select="@ID" />
        <br />
    </xsl:template>

    <xsl:template name="MachineID">
        <xsl:if test="Machine">
            Machine:
            <xsl:value-of select="Machine/@InternalCode" />
            <br />
        </xsl:if>
    </xsl:template>

    <xsl:template match="Machine/ConfigurationFiles/ConfigurationFile">
        <p>
            Machine:
            <xsl:value-of select="../../@InternalCode" />
            <br />
            Descrition:
            <xsl:value-of select="." />
            <br />
        </p>
    </xsl:template>

    <xsl:template match="Wastes">
        <h3>Wastes</h3>
        <xsl:apply-templates select="Waste" />
    </xsl:template>

    <xsl:template match="Waste">
        <p>
            Production Order:
            <xsl:value-of select="ProductionOrder/@ID" />
            <br />
            Machine:
            <xsl:value-of select="Machine/@InternalCode" />
            <br />
            Quatity:
            <xsl:value-of select="@quantity" />
            <br />
            <xsl:if test="Product">
                Product:
                <xsl:value-of select="Product/@ID" />
                <br />
            </xsl:if>
            <xsl:if test="RawMaterial">
                Raw Material:
                <xsl:value-of select="RawMaterial/@ID" />
                <br />
            </xsl:if>
            Deposit:
            <xsl:value-of select="Deposit/@ID" />
            <br />
        </p>
    </xsl:template>

    <xsl:template match="EffectiveTimes">
        <h3>Effective Times</h3>
        <xsl:apply-templates select="EffectiveTime" />
    </xsl:template>

    <xsl:template match="EffectiveTime">
        <p>
            Production Order:
            <xsl:value-of select="ProductionOrder/@ID" />
            <br />
            Machine:
            <xsl:value-of select="Machine/@InternalCode" />
            <br />
            Minutes:
            <xsl:value-of select="@Minutes" />
            <br />
            Seconds:
            <xsl:value-of select="@Seconds" />
            <br />
        </p>
    </xsl:template>

    <xsl:template match="BruteTimes">
        <h3>Brute Times</h3>
        <xsl:apply-templates select="BruteTime" />
    </xsl:template>

    <xsl:template match="BruteTime">
        <p>
            Production Order:
            <xsl:value-of select="ProductionOrder/@ID" />
            <br />
            Machine:
            <xsl:value-of select="Machine/@InternalCode" />
            <br />
            Minutes:
            <xsl:value-of select="@Minutes" />
            <br />
            Seconds:
            <xsl:value-of select="@Seconds" />
            <br />
        </p>
    </xsl:template>


</xsl:stylesheet>
