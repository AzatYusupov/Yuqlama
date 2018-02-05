package com.example.avto.yuqlama.models;

import android.content.Intent;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by AzatYusupov on 24.01.2018.
 */

public class Migration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();
        RealmObjectSchema groupSchema = schema.create("Group");

        groupSchema.addField("ID", Integer.class);
        groupSchema.addField("NAME", String.class, FieldAttribute.REQUIRED);
        groupSchema.addField("COURSE_ID", Integer.class);

        RealmObjectSchema studentSchema = schema.create("Student");
        studentSchema.addField("ID", Integer.class);
        studentSchema.addField("NAME", String.class, FieldAttribute.REQUIRED);
        studentSchema.addField("GROUP_ID", Integer.class);

    }
}
