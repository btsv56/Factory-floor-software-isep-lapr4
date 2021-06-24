<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : XMLtoXHTML.xsl
    Created on : 4 de Junho de 2020, 21:35
    Author     : Utilizador
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/SSFM">
        <html>
            <h1>Factory Floor</h1>
            <body>
                <xsl:apply-templates select="RawMaterialCategories"/>
                <xsl:apply-templates select="RawMaterials"/>
                <xsl:apply-templates select="Consumptions"/>
            </body> 
        </html>
    </xsl:template>
        
    <!-- Raw Materials Categories-->
    <xsl:template match="RawMaterialCategories">
        <table border="1">
            <h2>Raw Material Categories</h2>
            <tr>
                <th>Name</th>
                <th>Description</th>
            </tr>
            <xsl:apply-templates  select="RawMaterialCategory">
                <xsl:sort select="@name" data-type="text" order="ascending"/>
            </xsl:apply-templates>
        </table>
    </xsl:template>
   
    <xsl:template match="RawMaterialCategory">
        <tr>
            <td>
                <xsl:value-of select="@name"/>
            </td>
            <td>
                <xsl:value-of select="description"/>
            </td>
        </tr>
    </xsl:template>
    
    <!-- Raw Materials -->
    <xsl:template match="RawMaterials">
        <table border="1">
            <h2>Raw Materials</h2>
            <tr>
                <th>Internal Code</th>
                <th>Category</th>
                <th>Description</th>
                <th>Technical Sheet</th>
            </tr>
            <xsl:apply-templates select="RawMaterial">
                <xsl:sort select="@interalCode" data-type="text" order="ascending"/>
            </xsl:apply-templates>
        </table>
    </xsl:template>
    
    <xsl:template match="RawMaterial">
        <tr>
            <td>
                <xsl:value-of select="@internalCode"/>
            </td>
            <td>
                <xsl:value-of select="@nameRawMaterialCategory"/>
            </td>
            <td>
                <xsl:value-of select="description"/>
            </td>
            <td>
                <xsl:value-of select="TechnicalSheet/nameTechnicalSheet"/>
            </td>
        </tr>
    </xsl:template>



    <!-- Consumptions-->
    <xsl:template match="Consumptions">
        <h2>Consumptions</h2>
        <xsl:apply-templates select="EffectiveConsumptions"/>
        <xsl:apply-templates select="RealConsumptions"/>   
    </xsl:template>
    
    <!-- Effective Consumptions -->
    <xsl:template match="EffectiveConsumptions">
        <table border="1">
            <h2>Effective Consumptions</h2>
            <tr>
                <th>Machine</th>
                <th>Deposit</th>
                <th>Raw Material</th>
                <th>Quantity</th>
            </tr>
            <xsl:apply-templates select="Consumption"/>
        </table>
    </xsl:template>
    
    <xsl:template match="Consumption">
        <tr>
            <td>
                <xsl:value-of select="@machine"/>
            </td>
            <td>
                <xsl:value-of select="@deposit"/>
            </td>
            <td>
                <xsl:value-of select="@rawMaterial"/>
                <xsl:value-of select="@product"/>
            </td>
            <td>
                <xsl:value-of select="quantity"/>
            </td>
        </tr>
    </xsl:template>
    
    <!-- RealConsumptions -->
    <xsl:template match="RealConsumptions">
        <h2>Real Consumptions</h2>
        <xsl:call-template name="DepositConsumption"/>
        <xsl:call-template name="RawMaterialConsumption"/>
        <xsl:call-template name="ProductConsumption"/>
    </xsl:template>
    
    <xsl:template name="RawMaterialConsumption">
        <h3>Raw Material Consumptions</h3>
        <Table border="1">
            <tr>
                <th>Raw Material</th>
                <th>Quantity</th>
            </tr>
            <xsl:apply-templates select="RawMaterial"/>
        </Table>
    </xsl:template>
    
    <xsl:template match="//RealConsumptions/RawMaterial">
        <tr>
            <td>
                <xsl:value-of select="@id"/>
            </td>
            <td>
                <xsl:value-of select="quantity"/>
            </td>
        </tr>
    </xsl:template>
    
    <xsl:template name="ProductConsumption">
        <h3>Product Consumptions</h3>
        <Table border="1">
            <tr>
                <th>Product</th>
                <th>Quantity</th>
            </tr>
            <xsl:apply-templates select="Product"/>
        </Table>
    </xsl:template>
    
    <xsl:template match="//RealConsumptions/Product">
        <tr>
            <td>
                <xsl:value-of select="@id"/>
            </td>
            <td>
                <xsl:value-of select="quantity"/>
            </td>
        </tr>
    </xsl:template>
    
    <xsl:template name="DepositConsumption">
        <table border="1">
            <h3>Deposit Consumptions</h3>
            <tr>
                <th>Deposit</th>
                <th>Quantity</th>
            </tr>
            <xsl:for-each select="Deposit">
                <tr>
                    <td>
                        <xsl:value-of select="@id"/>
                    </td>
                    <td>
                        <xsl:value-of select="quantity"/>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>
</xsl:stylesheet>
