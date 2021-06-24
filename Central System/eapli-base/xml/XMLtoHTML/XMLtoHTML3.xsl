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
                <h2>Items</h2>
                <table border="1">
                    <tr>
                        <th>Type of information</th>
                        <th>Quantity</th>
                    </tr>
                    <xsl:apply-templates select="RawMaterialCategories" />
                    <xsl:apply-templates select="RawMaterials" />
                    <xsl:apply-templates select="Products" />
                    <xsl:apply-templates select="Deposits" />
                    <xsl:apply-templates select="ProductionSheets" />
                    <xsl:apply-templates select="Lots" />
                    <xsl:apply-templates select="ProductionOrders" />
                    <xsl:apply-templates select="ProductionLines" />
                </table>
                <h2>Stock changes</h2>
                <xsl:apply-templates select="Consumptions" />
                <h2>Production Orders' Results</h2>
                <xsl:call-template name="Results" />
            </body>
        </html>
    </xsl:template>

    <!-- Raw Materials Categories-->
    <xsl:template match="RawMaterialCategories">
        <tr>
            <td>Raw Material Categories</td>
            <td>
                <xsl:value-of select="count(RawMaterialCategory)" />
            </td>
        </tr>
    </xsl:template>

    <!-- Raw Materials -->
    <xsl:template match="RawMaterials">
        <tr>
            <td>Raw Materials</td>
            <td>
                <xsl:value-of select="count(RawMaterial)" />
            </td>
        </tr>
    </xsl:template>

    <!-- Products -->
    <xsl:template match="Products">
        <tr>
            <td>Products</td>
            <td>
                <xsl:value-of select="count(Product)" />
            </td>
        </tr>
    </xsl:template>

    <!-- Deposits -->
    <xsl:template match="Deposits">
        <tr>
            <td>Deposits</td>
            <td>
                <xsl:value-of select="count(Deposit)" />
            </td>
        </tr>
    </xsl:template>

    <!-- Production Sheets -->
    <xsl:template match="ProductionSheets">
        <tr>
            <td>Production Sheets</td>
            <td>
                <xsl:value-of select="count(ProductionSheet)" />
            </td>
        </tr>
    </xsl:template>

    <!-- Lots -->
    <xsl:template match="Lots">
        <tr>
            <td>Lots</td>
            <td>
                <xsl:value-of select="count(Lot)" />
            </td>
        </tr>
    </xsl:template>

    <!-- Production Orders -->
    <xsl:template match="ProductionOrders">
        <tr>
            <td>Production Orders</td>
            <td>
                <xsl:value-of select="count(ProductionOrder)" />
            </td>
        </tr>
    </xsl:template>

    <!-- Production Lines -->
    <xsl:template match="ProductionLines">
        <tr>
            <td>Production Lines</td>
            <td>
                <xsl:value-of select="count(ProductionLine)" />
            </td>
        </tr>
    </xsl:template>

    <!-- Consumptions -->
    <xsl:template match="Consumptions">
        <xsl:apply-templates select="EffectiveConsumptions" />
    </xsl:template>

    <xsl:template match="EffectiveConsumptions">
        <a>
            Total consumption:
            <xsl:value-of select="sum(Consumption/quantity)" />
            <br />
            Average Consumption:
            <xsl:value-of select="avg(Consumption/quantity)" />
            <h3>Smallest Consumption</h3>
            <xsl:variable name="min">
                <xsl:value-of select="min(Consumption/quantity)" />
            </xsl:variable>
            <xsl:variable name="material">
                <xsl:value-of select="(Consumption/@rawMaterial[$min=(../quantity)])[1]" />
            </xsl:variable>
            <xsl:variable name="materialMax">
                <xsl:value-of select="(Consumption/@rawMaterial[$min=(../quantity)])[1]" />
            </xsl:variable>
            <ul>
                <li>
                    Quantity:
                    <xsl:value-of select="min(Consumption/quantity)" />
                </li>
                <li>
                    Machine:
                    <xsl:value-of select="(Consumption/@machine[$min=(../quantity)])[1]" />
                </li>
                <li>
                    <xsl:if test="Consumption/@deposit">
                        Deposit:
                        <xsl:value-of select="(Consumption/@deposit[$min=(../quantity)])[1]" />
                    </xsl:if>
                </li>
                <li>
                    Raw material:
                    <xsl:choose>
                        <xsl:when test="$material!=''">
                            <xsl:value-of select="$material" />
                        </xsl:when>
                        <xsl:when test="$material=''">
                            <xsl:value-of select="(Consumption/@product[$min=(../quantity)])[1]" />
                        </xsl:when>
                    </xsl:choose>
                </li>
            </ul>
            <h3>Biggest Consumption</h3>
            <xsl:variable name="max">
                <xsl:value-of select="min(Consumption/quantity)" />
            </xsl:variable>
            <ul>
                <li>
                    Quantity:
                    <xsl:value-of select="max(Consumption/quantity)" />
                </li>
                <li>
                    Machine:
                    <xsl:value-of select="(Consumption/@machine[$max=(../quantity)])[1]" />
                </li>
                <li>
                    <xsl:if test="Consumption/@deposit">
                        Deposit:
                        <xsl:value-of select="(Consumption/@deposit[$max=(../quantity)])[1]" />
                    </xsl:if>
                </li>
                <li>
                    Raw material:
                    <xsl:choose>
                        <xsl:when test="$material!=''">
                            <xsl:value-of select="$material" />
                        </xsl:when>
                        <xsl:when test="$material=''">
                            <xsl:value-of select="(Consumption/@product[$max=(../quantity)])[1]" />
                        </xsl:when>
                    </xsl:choose>
                </li>
            </ul>
        </a>
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
                <h3>
                    Production Order:
                    <strong>
                        <xsl:value-of select="@internalCode" />
                    </strong>
                </h3>
                <ul>
                    <li>
                        Machine with the highest effective time:
                        <xsl:value-of select="//EffectiveTime/Machine[../ProductionOrder/@ID=$orderID and ../@Minutes=$maxEffectiveMinutes and ../@Seconds=$maxEffectiveSeconds]/@InternalCode" />
                    </li>
                    <li>
                        Machine with the lowest effective time:
                        <xsl:value-of select="//EffectiveTime/Machine[../ProductionOrder/@ID=$orderID and ../@Minutes=$minEffectiveMinutes and ../@Seconds=$minEffectiveSeconds]/@InternalCode" />
                    </li>
                    <li>
                        Total DownTime:
                        <xsl:value-of select="abs($sumBruteTimesMinutes - $sumEffectiveTimesMinutes)" />
                        :
                        <xsl:value-of select="abs($sumBruteTimesSeconds - $sumEffectiveTimesSeconds)" />
                    </li>
                </ul>
            </xsl:if>

        </xsl:for-each>
    </xsl:template>


</xsl:stylesheet>
