<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : XMLtoXML.xsl
    Created on : 5 de Junho de 2020, 17:55
    Author     : Utilizador
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
<xsl:output method="xml"/>
    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/SSFM">
        <SSFM>
            <xsl:apply-templates select="RawMaterialCategories"/>
            <xsl:apply-templates select="RawMaterials"/>
            <xsl:apply-templates select="Products"/>
            <xsl:apply-templates select="Deposits"/>
            <xsl:apply-templates select="ProductionSheets"/>
            <xsl:apply-templates select="Lots"/>
            <xsl:apply-templates select="ProductionOrders"/>
            <xsl:apply-templates select="ProductionLines"/>
            <xsl:apply-templates select="Consumptions"/>
            <xsl:apply-templates select="Machines"/>
        </SSFM>
    </xsl:template>
    
    <!-- Raw Material Categories -->
    <xsl:template match="RawMaterialCategories">
        <RawMaterialCategories>
            <xsl:attribute name="quantity">
                <xsl:value-of select="count(RawMaterialCategory)"/>
            </xsl:attribute> 
            <xsl:apply-templates select="RawMaterialCategory"/>
        </RawMaterialCategories>
    </xsl:template>
    
    <xsl:template match="RawMaterialCategory">
        <RawMaterialCategory>
            <xsl:attribute name="id">
                <xsl:value-of select="@name"/>
            </xsl:attribute>
            <xsl:attribute name="description">
                <xsl:value-of select="description"/>
            </xsl:attribute>
        </RawMaterialCategory>
    </xsl:template>
    
    <!-- Raw Materials -->
    <xsl:template match="RawMaterials">
        <RawMaterials>
            <xsl:attribute name="quantity">
                <xsl:value-of select="count(RawMaterial)"/>
            </xsl:attribute>
            <xsl:apply-templates select="RawMaterial"/>
        </RawMaterials>
    </xsl:template>
    
    <xsl:template match="RawMaterial">
        <RawMaterial>
            <xsl:attribute name="id">
                <xsl:value-of select="@internalCode"/>
            </xsl:attribute>
            <xsl:attribute name="category">
                <xsl:value-of select="@nameRawMaterialCategory"/>
            </xsl:attribute>
            <xsl:attribute name="description">
                <xsl:value-of select="description"/>
            </xsl:attribute>
            <xsl:element name="TechnicalSheet">
                <xsl:value-of select="TechnicalSheet/nameTechnicalSheet"/>
            </xsl:element>
        </RawMaterial>
    </xsl:template>

    <!-- Products -->
    <xsl:template match="Products">
        <Products>
            <xsl:attribute name="Quantity">
                <xsl:value-of select="count(Product)"/>
            </xsl:attribute>
            <xsl:apply-templates select="Product"/>
        </Products>
    </xsl:template>

    <xsl:template match="Product">
        <Products>
            <xsl:attribute name="fabricationCode">
                <xsl:value-of select="@fabricationCode"/>
            </xsl:attribute>
            <xsl:attribute name="comercialCode">
                <xsl:value-of select="comercialCode"/>
            </xsl:attribute>
            <xsl:attribute name="unity">
                <xsl:value-of select="unity"/>
            </xsl:attribute>
            <xsl:attribute name="productCategory">
                <xsl:value-of select="productCategory"/>
            </xsl:attribute>
            <xsl:element name="briefDescription">
                <xsl:value-of select="briefDescription"/>
            </xsl:element>
            <xsl:element name="completeDescription">
                <xsl:value-of select="completeDescription"/>
            </xsl:element>
            <xsl:element name="productionSheet">
                <xsl:value-of select="productionSheet"/>
            </xsl:element>
        </Products>
    </xsl:template>

    <!-- Deposits -->
    <xsl:template match="Deposits">
        <Deposits>
            <xsl:attribute name="quantity">
                <xsl:value-of select="count(Deposit)"/>
            </xsl:attribute>
            <xsl:apply-templates select="Deposit"/>
        </Deposits>
    </xsl:template>

    <xsl:template match="Deposit">
        <Deposit>
            <xsl:attribute name="ID">
                <xsl:value-of select="@internalCode"/>
            </xsl:attribute>
            <xsl:element name="Description">
                <xsl:value-of select="Description"/>
            </xsl:element>
            <xsl:element name="Objects">
                <xsl:value-of select="sum(RawMaterials/RawMaterial/@Quantity)+sum(Products/Product/@Quantity)"/>
            </xsl:element>
        </Deposit>
    </xsl:template>

    <!-- Lots -->
    <xsl:template match="Lots">
        <Lots>
            <xsl:attribute name="quantity">
                <xsl:value-of select="count(Lot)"/>
            </xsl:attribute>
            <xsl:apply-templates select="Lot"/>
        </Lots>
    </xsl:template>

    <xsl:template match="Lot">
        <Lot>
            <xsl:attribute name="ID">
                <xsl:value-of select="@internalCode"/>
            </xsl:attribute>
        </Lot>
    </xsl:template>

    <!-- Production Orders -->
    <xsl:template match="ProductionOrders">
        <ProductionOrders>
            <xsl:attribute name="quantity">
                <xsl:value-of select="count(ProductionOrder)"/>
            </xsl:attribute>
            <xsl:apply-templates select="ProductionOrder"/>
        </ProductionOrders>
    </xsl:template>

    <xsl:template match="ProductionOrder">
        <ProductionOrder>
            <xsl:attribute name="ID">
                <xsl:value-of select="@internalCode"/>
            </xsl:attribute>
            <xsl:element name="Description">
                <xsl:value-of select="Description"/>
            </xsl:element>
            <xsl:element name="State">
                <xsl:value-of select="State"/>
            </xsl:element>
            <Lot>
                <xsl:attribute name="ID">
                    <xsl:value-of select="@ID"/>
                </xsl:attribute>
            </Lot>
            <ProductionSheet>
                <xsl:attribute name="ID">
                    <xsl:value-of select="@ID"/>
                </xsl:attribute>
            </ProductionSheet>
            <xsl:element name="QuantityToProduce">
                <xsl:value-of select="QuantityToProduce"/>
            </xsl:element>
        </ProductionOrder>
    </xsl:template>

    <!-- Production Lines -->
    <xsl:template match="ProductionLines">
        <ProductionLines>
            <xsl:attribute name="quantity">
                <xsl:value-of select="count(ProductionLine)"/>
            </xsl:attribute>
            <xsl:apply-templates select="ProductionLine"/>
        </ProductionLines>
    </xsl:template>

    <xsl:template match="ProductionLine">
        <ProductionLine>
            <xsl:attribute name="ID">
                <xsl:value-of select="@internalCode"/>
            </xsl:attribute>
            <xsl:element name="Description">
                <xsl:value-of select="Description"/>
            </xsl:element>
            <Machines>
                <xsl:attribute name="Machines">
                    <xsl:value-of select="count(Machines/Machine)"/>
                </xsl:attribute>
            </Machines>
        </ProductionLine>
    </xsl:template>

    <!-- Production Sheets -->
    <xsl:template match="ProductionSheets">
        <ProductionSheets>
            <xsl:attribute name="Quantity">
                <xsl:value-of select="count(ProductionSheet)"/>
            </xsl:attribute>
            <xsl:apply-templates select="Product"/>
        </ProductionSheets>
    </xsl:template>

    <xsl:template match="ProductionSheet">
        <ProductionSheet>
            <xsl:attribute name="ID">
                <xsl:value-of select="@ID"/>
            </xsl:attribute>
            <Products>
                <xsl:attribute name="Quantity">
                    <xsl:value-of select="count(Product)"/>
                </xsl:attribute>
                <xsl:apply-templates select="ProductionSheetLineProduct"/>
            </Products>
            <RawMaterials>
                <xsl:attribute name="Quantity">
                    <xsl:value-of select="count(RawMaterial)"/>
                </xsl:attribute>
            </RawMaterials>
        </ProductionSheet>
    </xsl:template>

    <xsl:template match="ProductionSheetLineProduct">
        <xsl:for-each select="Product">
            <xsl:value-of select="@ID"/>
            <xsl:value-of select="@Quantity"/>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="ProductionSheetLineRawMaterial">
        <xsl:for-each select="RawMaterial">
            <xsl:value-of select="@ID"/>
            <xsl:value-of select="@Quantity"/>
        </xsl:for-each>
    </xsl:template>
    
    <!-- Consumptions -->
    <xsl:template match="Consumptions">
        <Consumptions>
            <xsl:apply-templates select="EffectiveConsumptions"/> 
            <xsl:apply-templates select="RealConsumptions"/>
        </Consumptions>
    </xsl:template>
    
    <xsl:template match="EffectiveConsumptions">
        <EffectiveConsumptions> 
            <xsl:attribute name="totalConsumtpion">
                <xsl:value-of select="sum(Consumption/quantity)"/>
            </xsl:attribute>
            <xsl:attribute name="averageConsumption">
                <xsl:value-of select="avg(Consumption/quantity)"/>
            </xsl:attribute>
            <xsl:element name="SmallestConsumption">
                <xsl:variable name="min">
                    <xsl:value-of select="min(Consumption/quantity)"/>
                </xsl:variable>
                <xsl:attribute name="value"> 
                    <xsl:value-of select="min(Consumption/quantity)"/>
                </xsl:attribute>
                <xsl:attribute name="machine">
                    <xsl:value-of select="(Consumption/@machine[$min=(../quantity)])[1]"/>
                </xsl:attribute>
                <xsl:attribute name="deposit">
                    <xsl:value-of select="(Consumption/@deposit[$min=(../quantity)])[1]"/>
                </xsl:attribute>
                <xsl:variable name="material">
                    <xsl:value-of select="(Consumption/@rawMaterial[$min=(../quantity)])[1]"/>
                </xsl:variable>
                <xsl:attribute name="rawMaterial"> 
                    <xsl:choose>
                        <xsl:when test="$material!=''">
                            <xsl:value-of select="$material"/>
                        </xsl:when>
                        <xsl:when test="$material=''">
                            <xsl:value-of select="(Consumption/@product[$min=(../quantity)])[1]"/>
                        </xsl:when>
                    </xsl:choose>
                </xsl:attribute>
            </xsl:element>
            <xsl:element name="BiggestConsumptions">
                <xsl:variable name="max">
                    <xsl:value-of select="max(Consumption/quantity)"/>
                </xsl:variable>
                <xsl:attribute name="value"> 
                    <xsl:value-of select="max(Consumption/quantity)"/>
                </xsl:attribute>
                <xsl:attribute name="machine"> 
                    <xsl:value-of select="(Consumption[1]/@machine[$max=(../quantity)])[1]"/>
                </xsl:attribute>
                <xsl:attribute name="deposit"> 
                    <xsl:value-of select="(Consumption/@deposit[$max=(../quantity)])[1]"/>
                </xsl:attribute>
                <xsl:variable name="materialMax">
                    <xsl:value-of select="(Consumption/@rawMaterial[$max=(../quantity)])[1]"/>
                </xsl:variable>
                <xsl:attribute name="rawMaterial"> 
                    <xsl:choose>
                        <xsl:when test="$materialMax!=''">
                            <xsl:value-of select="(Consumption/@rawMaterial[$max=(../quantity)])[1]"/>
                        </xsl:when>
                        <xsl:when test="$materialMax=''">
                            <xsl:value-of select="(Consumption/@product[$max=(../quantity)])[1]"/>
                        </xsl:when>
                    </xsl:choose>
                </xsl:attribute>
            </xsl:element>           
        </EffectiveConsumptions>
    </xsl:template>
    
    <!-- Real Consumptions -->
    <xsl:template match="RealConsumptions">
        <RealConsumptions>
            <xsl:apply-templates select="Deposit"/>
            <xsl:apply-templates select="Product"/>
            <xsl:apply-templates select="RawMaterial"/>
        </RealConsumptions>
    </xsl:template>
    
    <xsl:template match="//RealConsumptions/RawMaterial">
        <Product>
            <xsl:attribute name="rawMaterial">
                <xsl:value-of select="@id"/>
            </xsl:attribute>
            <xsl:attribute name="quantity">
                <xsl:value-of select="quantity"/>
            </xsl:attribute>
        </Product>
    </xsl:template>
    
    <xsl:template match="//RealConsumptions/Product">
        <Product>
            <xsl:attribute name="product">
                <xsl:value-of select="@id"/>
            </xsl:attribute>
            <xsl:attribute name="quantity">
                <xsl:value-of select="quantity"/>
            </xsl:attribute>
        </Product>
    </xsl:template>
    
    <xsl:template match="//RealConsumptions/Deposit">
        <Deposit>
            <xsl:attribute name="deposit">
                <xsl:value-of select="@id"/>   
            </xsl:attribute>
            <xsl:attribute name="quantity">
                <xsl:value-of select="quantity"/>   
            </xsl:attribute>
        </Deposit>
    </xsl:template>

    <xsl:template match="Machines">
        <Machines>
            <xsl:apply-templates select="Machine" />
        </Machines>
    </xsl:template>

    <xsl:template match="Machine">
        
        <Machine>
            <xsl:attribute name="InternalCode">
                <xsl:value-of select="@InternalCode"/>
            </xsl:attribute>
            <xsl:attribute name="State">
                <xsl:value-of select="@State"/>
            </xsl:attribute>
            <SerialNumber><xsl:value-of select="SerialNumber" /></SerialNumber>
            <Description><xsl:value-of select="Description" /></Description>
            <InstallationDate><xsl:value-of select="InstallationDate" /></InstallationDate>
            <Brand><xsl:value-of select="Brand" /></Brand>
            <Model><xsl:value-of select="Model" /></Model>
            <xsl:if test="Protocol">
            <Protocol><xsl:value-of select="Protocol/@ID" /></Protocol>
            </xsl:if>
            <xsl:if test="Protocol">
            <Machine><xsl:value-of select="Machine/@InternalCode" /></Machine>
            </xsl:if>
            <ConfigurationFiles>
                <xsl:for-each select="ConfigurationFiles/ConfigurationFile">
                <ConfigurationFile><xsl:attribute name="file"><xsl:value-of select="@File"/></xsl:attribute><xsl:value-of select="." /></ConfigurationFile>
                </xsl:for-each>
            </ConfigurationFiles>
        </Machine>

    </xsl:template>


</xsl:stylesheet>
