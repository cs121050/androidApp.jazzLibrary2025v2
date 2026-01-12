package com.example.jazzlibrary2025v2.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Type {

    @SerializedName("type_id")
    private int typeId;  // Changed to camelCase
    @SerializedName("type_name")
    private String typeName;
    @SerializedName("type_video_count")
    private int typeVideoCount;

    // Constructors
    public Type() {}

    public Type(int typeId, String typeName, int typeVideoCount) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.typeVideoCount = typeVideoCount;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypeVideoCount() {
        return typeVideoCount;
    }

    public void setTypeVideoCount(int typeVideoCount) {
        this.typeVideoCount = typeVideoCount;
    }


    @Override
    public String toString() {
        return "Type{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", typeVideoCount=" + typeVideoCount +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return typeId == type.typeId && typeVideoCount == type.typeVideoCount && Objects.equals(typeName, type.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeId, typeName, typeVideoCount);
    }
}
