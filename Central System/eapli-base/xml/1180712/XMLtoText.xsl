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
        <xsl:apply-templates select="RawMaterialCategories"/>
        <xsl:apply-templates select="RawMaterials"/>
        <xsl:apply-templates select="Consumptions"/>
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

</xsl:stylesheet>
