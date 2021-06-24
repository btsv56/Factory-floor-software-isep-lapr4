<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : XMLtoXHTML2.xsl
    Created on : 6 de Junho de 2020, 14:34
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
        Factory floor:
        
        Items:
                   
        <xsl:apply-templates select="RawMaterialCategories"/>
        <xsl:apply-templates select="RawMaterials"/>
        <xsl:apply-templates select="Products"/>
        <xsl:apply-templates select="Deposits"/>
        <xsl:apply-templates select="Lots"/>
        <xsl:apply-templates select="ProductionOrders"/>
        <xsl:apply-templates select="ProductionLines"/>
        <xsl:apply-templates select="ProductionSheets"/>

        Stock changes:
        
        <xsl:apply-templates select="Consumptions"/>

        Results:
        <xsl:call-template name="Results"/>
    </xsl:template>
    
    <!-- Raw Materials Categories-->
    <xsl:template match="RawMaterialCategories">
        Raw Material Categories: <xsl:value-of select="count(RawMaterialCategory)"/>
    </xsl:template>
       
    <!-- Raw Materials -->
    <xsl:template match="RawMaterials">
        Raw Materials: <xsl:value-of select="count(RawMaterial)"/>
    </xsl:template>

    <!-- Products -->
    <xsl:template match="Products">
        Products: <xsl:value-of select="count(Product)"/>
    </xsl:template>

    <!-- Deposits -->
    <xsl:template match="Deposits">
        Deposits: <xsl:value-of select="count(Deposit)"/>
    </xsl:template>

    <!-- Lots -->
    <xsl:template match="Lots">
        Lots: <xsl:value-of select="count(Lot)"/>
    </xsl:template>

    <!-- Production Orders -->
    <xsl:template match="ProductionOrders">
        Production Orders: <xsl:value-of select="count(ProductionOrder)"/>
    </xsl:template>

    <!-- Production Lines -->
    <xsl:template match="ProductionLines">
        Production Lines: <xsl:value-of select="count(ProductionLine)"/>
    </xsl:template>

    <!-- Production Sheets -->
    <xsl:template match="ProductionSheets">
        Production Sheets: <xsl:value-of select="count(ProductionSheet)"/>
    </xsl:template>
    
    <!-- Consumptions -->
    <xsl:template match="Consumptions">
        <xsl:apply-templates select="EffectiveConsumptions"/>
    </xsl:template>
    
    <xsl:template match="EffectiveConsumptions">
        Total consumption: <xsl:value-of select="sum(Consumption/quantity)"/>
        Average Consumption: <xsl:value-of select="avg(Consumption/quantity)"/>
        
        Smallest Consumption:
        <xsl:variable name="min">
            <xsl:value-of select="min(Consumption/quantity)"/>
        </xsl:variable>
        <xsl:variable name="material">
            <xsl:value-of select="(Consumption/@rawMaterial[$min=(../quantity)])[1]"/>
        </xsl:variable>
        <xsl:variable name="materialMax">
            <xsl:value-of select="(Consumption/@rawMaterial[$min=(../quantity)])[1]"/>
        </xsl:variable>
        Quantity: <xsl:value-of select="min(Consumption/quantity)"/>
        Machine:  <xsl:value-of select="(Consumption/@machine[$min=(../quantity)])[1]"/>
        <xsl:if test="Consumption/@deposit">
        Deposit: <xsl:value-of select="(Consumption/@deposit[$min=(../quantity)])[1]"/>
        </xsl:if>
        Raw material:
        <xsl:choose>
            <xsl:when test="$material!=''">
                <xsl:value-of select="$material"/>
            </xsl:when>
            <xsl:when test="$material=''">
                <xsl:value-of select="(Consumption/@product[$min=(../quantity)])[1]"/>
            </xsl:when>
        </xsl:choose>
        
        Biggest Consumption:
        <xsl:variable name="max">
            <xsl:value-of select="min(Consumption/quantity)"/>
        </xsl:variable>
        Quantity: <xsl:value-of select="max(Consumption/quantity)"/>
        Machine:  <xsl:value-of select="(Consumption/@machine[$max=(../quantity)])[1]"/>
        <xsl:if test="Consumption/@deposit">
        Deposit: <xsl:value-of select="(Consumption/@deposit[$max=(../quantity)])[1]"/>
        </xsl:if>
        Raw material:
        <xsl:choose>
            <xsl:when test="$material!=''">
                <xsl:value-of select="$material"/>
            </xsl:when>
            <xsl:when test="$material=''">
                <xsl:value-of select="(Consumption/@product[$max=(../quantity)])[1]"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="Results">
        <xsl:for-each select="//ProductionOrders/ProductionOrder">
            <xsl:variable name="orderID">
                <xsl:value-of select="./@internalCode" />
            </xsl:variable>

            <xsl:if test="//EffectiveTime/ProductionOrder/@ID=$orderID">
                <xsl:variable name="maxEffectiveMinutes">
                    <xsl:value-of select="max(//EffectiveTime[ProductionOrder/@ID=$orderID]/@Minutes)" />
                </xsl:variable>
                <xsl:variable name="maxEffectiveSeconds">
                    <xsl:value-of select="max(//EffectiveTime[ProductionOrder/@ID=$orderID and @Minutes=$maxEffectiveMinutes]/@Seconds)" />
                </xsl:variable>
                <xsl:variable name="minEffectiveMinutes">
                    <xsl:value-of select="min(//EffectiveTime[ProductionOrder/@ID=$orderID]/@Minutes)" />
                </xsl:variable>
                <xsl:variable name="minEffectiveSeconds">
                    <xsl:value-of select="min(//EffectiveTime[ProductionOrder/@ID=$orderID and @Minutes=$minEffectiveMinutes]/@Seconds)" />
                </xsl:variable>
                <xsl:variable name="sumEffectiveTimesMinutes">
                    <xsl:value-of select="sum(//EffectiveTime[ProductionOrder/@ID=$orderID]/@Minutes)" />
                </xsl:variable>
                <xsl:variable name="sumEffectiveTimesSeconds">
                    <xsl:value-of select="sum(//EffectiveTime[ProductionOrder/@ID=$orderID]/@Seconds)" />
                </xsl:variable>
                <xsl:variable name="sumBruteTimesMinutes">
                    <xsl:value-of select="sum(//BruteTime[ProductionOrder/@ID=$orderID]/@Minutes)" />
                </xsl:variable>
                <xsl:variable name="sumBruteTimesSeconds">
                    <xsl:value-of select="sum(//BruteTime[ProductionOrder/@ID=$orderID]/@Seconds)" />
                </xsl:variable>
                
                Production Order: <xsl:value-of select="@internalCode" />
                
                Machine with the highest effective time: <xsl:value-of select="//EffectiveTime/Machine[../ProductionOrder/@ID=$orderID and ../@Minutes=$maxEffectiveMinutes and ../@Seconds=$maxEffectiveSeconds]/@InternalCode" />
                Machine with the lowest effective time: <xsl:value-of select="//EffectiveTime/Machine[../ProductionOrder/@ID=$orderID and ../@Minutes=$minEffectiveMinutes and ../@Seconds=$minEffectiveSeconds]/@InternalCode" />
                Total DownTime: <xsl:value-of select="abs($sumBruteTimesMinutes - $sumEffectiveTimesMinutes)" />:<xsl:value-of select="abs($sumBruteTimesSeconds - $sumEffectiveTimesSeconds)" />
            </xsl:if>

        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>
