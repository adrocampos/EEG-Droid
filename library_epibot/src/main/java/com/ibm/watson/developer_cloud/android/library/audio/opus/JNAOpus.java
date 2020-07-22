/*
 * Copyright 2017 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.ibm.watson.developer_cloud.android.library.audio.opus;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import com.sun.jna.ptr.ShortByReference;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * The Interface JNAOpus.
 */
public interface JNAOpus extends Library {

    /**
     * The Constant JNA_LIBRARY_NAME.
     */
    String JNA_LIBRARY_NAME = "opus";

    /**
     * The Constant JNA_NATIVE_LIB.
     */
    NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(JNAOpus.JNA_LIBRARY_NAME);

    /**
     * The Constant INSTANCE.
     */
    JNAOpus INSTANCE = (JNAOpus) Native.loadLibrary(JNAOpus.JNA_LIBRARY_NAME, JNAOpus.class);

    /**
     * The Constant OPUS_GET_LSB_DEPTH_REQUEST.
     */
    int OPUS_GET_LSB_DEPTH_REQUEST = 4037;

    /**
     * The Constant OPUS_GET_APPLICATION_REQUEST.
     */
    int OPUS_GET_APPLICATION_REQUEST = 4001;

    /**
     * The Constant OPUS_GET_FORCE_CHANNELS_REQUEST.
     */
    int OPUS_GET_FORCE_CHANNELS_REQUEST = 4023;

    /**
     * The Constant OPUS_GET_VBR_REQUEST.
     */
    int OPUS_GET_VBR_REQUEST = 4007;

    /**
     * The Constant OPUS_GET_BANDWIDTH_REQUEST.
     */
    int OPUS_GET_BANDWIDTH_REQUEST = 4009;

    /**
     * The Constant OPUS_SET_BITRATE_REQUEST.
     */
    int OPUS_SET_BITRATE_REQUEST = 4002;

    /**
     * The Constant OPUS_SET_BANDWIDTH_REQUEST.
     */
    int OPUS_SET_BANDWIDTH_REQUEST = 4008;

    /**
     * The Constant OPUS_SIGNAL_MUSIC.
     */
    int OPUS_SIGNAL_MUSIC = 3002;

    /**
     * The Constant OPUS_RESET_STATE.
     */
    int OPUS_RESET_STATE = 4028;

    /**
     * The Constant OPUS_FRAMESIZE_2_5_MS.
     */
    int OPUS_FRAMESIZE_2_5_MS = 5001;

    /**
     * The Constant OPUS_GET_COMPLEXITY_REQUEST.
     */
    int OPUS_GET_COMPLEXITY_REQUEST = 4011;

    /**
     * The Constant OPUS_FRAMESIZE_40_MS.
     */
    int OPUS_FRAMESIZE_40_MS = 5005;

    /**
     * The Constant OPUS_SET_PACKET_LOSS_PERC_REQUEST.
     */
    int OPUS_SET_PACKET_LOSS_PERC_REQUEST = 4014;

    /**
     * The Constant OPUS_GET_VBR_CONSTRAINT_REQUEST.
     */
    int OPUS_GET_VBR_CONSTRAINT_REQUEST = 4021;

    /**
     * The Constant OPUS_SET_INBAND_FEC_REQUEST.
     */
    int OPUS_SET_INBAND_FEC_REQUEST = 4012;

    /**
     * The Constant OPUS_APPLICATION_RESTRICTED_LOWDELAY.
     */
    int OPUS_APPLICATION_RESTRICTED_LOWDELAY = 2051;

    /**
     * The Constant OPUS_BANDWIDTH_FULLBAND.
     */
    int OPUS_BANDWIDTH_FULLBAND = 1105;

    /**
     * The Constant OPUS_SET_VBR_REQUEST.
     */
    int OPUS_SET_VBR_REQUEST = 4006;

    /**
     * The Constant OPUS_BANDWIDTH_SUPERWIDEBAND.
     */
    int OPUS_BANDWIDTH_SUPERWIDEBAND = 1104;

    /**
     * The Constant OPUS_SET_FORCE_CHANNELS_REQUEST.
     */
    int OPUS_SET_FORCE_CHANNELS_REQUEST = 4022;

    /**
     * The Constant OPUS_APPLICATION_VOIP.
     */
    int OPUS_APPLICATION_VOIP = 2048;

    /**
     * The Constant OPUS_SIGNAL_VOICE.
     */
    int OPUS_SIGNAL_VOICE = 3001;

    /**
     * The Constant OPUS_GET_FINAL_RANGE_REQUEST.
     */
    int OPUS_GET_FINAL_RANGE_REQUEST = 4031;

    /**
     * The Constant OPUS_BUFFER_TOO_SMALL.
     */
    int OPUS_BUFFER_TOO_SMALL = -2;

    /**
     * The Constant OPUS_SET_COMPLEXITY_REQUEST.
     */
    int OPUS_SET_COMPLEXITY_REQUEST = 4010;

    /**
     * The Constant OPUS_FRAMESIZE_ARG.
     */
    int OPUS_FRAMESIZE_ARG = 5000;

    /**
     * The Constant OPUS_GET_LOOKAHEAD_REQUEST.
     */
    int OPUS_GET_LOOKAHEAD_REQUEST = 4027;

    /**
     * The Constant OPUS_GET_INBAND_FEC_REQUEST.
     */
    int OPUS_GET_INBAND_FEC_REQUEST = 4013;

    /**
     * The Constant OPUS_BITRATE_MAX.
     */
    int OPUS_BITRATE_MAX = -1;

    /**
     * The Constant OPUS_FRAMESIZE_5_MS.
     */
    int OPUS_FRAMESIZE_5_MS = 5002;

    /**
     * The Constant OPUS_BAD_ARG.
     */
    int OPUS_BAD_ARG = -1;

    /**
     * The Constant OPUS_GET_PITCH_REQUEST.
     */
    int OPUS_GET_PITCH_REQUEST = 4033;

    /**
     * The Constant OPUS_SET_SIGNAL_REQUEST.
     */
    int OPUS_SET_SIGNAL_REQUEST = 4024;

    /**
     * The Constant OPUS_FRAMESIZE_20_MS.
     */
    int OPUS_FRAMESIZE_20_MS = 5004;

    /**
     * The Constant OPUS_APPLICATION_AUDIO.
     */
    int OPUS_APPLICATION_AUDIO = 2049;

    /**
     * The Constant OPUS_GET_DTX_REQUEST.
     */
    int OPUS_GET_DTX_REQUEST = 4017;

    /**
     * The Constant OPUS_FRAMESIZE_10_MS.
     */
    int OPUS_FRAMESIZE_10_MS = 5003;

    /**
     * The Constant OPUS_SET_LSB_DEPTH_REQUEST.
     */
    int OPUS_SET_LSB_DEPTH_REQUEST = 4036;

    /**
     * The Constant OPUS_UNIMPLEMENTED.
     */
    int OPUS_UNIMPLEMENTED = -5;

    /**
     * The Constant OPUS_GET_PACKET_LOSS_PERC_REQUEST.
     */
    int OPUS_GET_PACKET_LOSS_PERC_REQUEST = 4015;

    /**
     * The Constant OPUS_INVALID_STATE.
     */
    int OPUS_INVALID_STATE = -6;

    /**
     * The Constant OPUS_SET_EXPERT_FRAME_DURATION_REQUEST.
     */
    int OPUS_SET_EXPERT_FRAME_DURATION_REQUEST = 4040;

    /**
     * The Constant OPUS_FRAMESIZE_60_MS.
     */
    int OPUS_FRAMESIZE_60_MS = 5006;

    /**
     * The Constant OPUS_GET_BITRATE_REQUEST.
     */
    int OPUS_GET_BITRATE_REQUEST = 4003;

    /**
     * The Constant OPUS_INTERNAL_ERROR.
     */
    int OPUS_INTERNAL_ERROR = -3;

    /**
     * The Constant OPUS_SET_MAX_BANDWIDTH_REQUEST.
     */
    int OPUS_SET_MAX_BANDWIDTH_REQUEST = 4004;

    /**
     * The Constant OPUS_SET_VBR_CONSTRAINT_REQUEST.
     */
    int OPUS_SET_VBR_CONSTRAINT_REQUEST = 4020;

    /**
     * The Constant OPUS_GET_MAX_BANDWIDTH_REQUEST.
     */
    int OPUS_GET_MAX_BANDWIDTH_REQUEST = 4005;

    /**
     * The Constant OPUS_BANDWIDTH_NARROWBAND.
     */
    int OPUS_BANDWIDTH_NARROWBAND = 1101;

    /**
     * The Constant OPUS_SET_GAIN_REQUEST.
     */
    int OPUS_SET_GAIN_REQUEST = 4034;

    /**
     * The Constant OPUS_SET_PREDICTION_DISABLED_REQUEST.
     */
    int OPUS_SET_PREDICTION_DISABLED_REQUEST = 4042;

    /**
     * The Constant OPUS_SET_APPLICATION_REQUEST.
     */
    int OPUS_SET_APPLICATION_REQUEST = 4000;

    /**
     * The Constant OPUS_SET_DTX_REQUEST.
     */
    int OPUS_SET_DTX_REQUEST = 4016;

    /**
     * The Constant OPUS_BANDWIDTH_MEDIUMBAND.
     */
    int OPUS_BANDWIDTH_MEDIUMBAND = 1102;

    /**
     * The Constant OPUS_GET_SAMPLE_RATE_REQUEST.
     */
    int OPUS_GET_SAMPLE_RATE_REQUEST = 4029;

    /**
     * The Constant OPUS_GET_EXPERT_FRAME_DURATION_REQUEST.
     */
    int OPUS_GET_EXPERT_FRAME_DURATION_REQUEST = 4041;

    /**
     * The Constant OPUS_AUTO.
     */
    int OPUS_AUTO = -1000;

    /**
     * The Constant OPUS_GET_SIGNAL_REQUEST.
     */
    int OPUS_GET_SIGNAL_REQUEST = 4025;

    /**
     * The Constant OPUS_GET_LAST_PACKET_DURATION_REQUEST.
     */
    int OPUS_GET_LAST_PACKET_DURATION_REQUEST = 4039;

    /**
     * The Constant OPUS_GET_PREDICTION_DISABLED_REQUEST.
     */
    int OPUS_GET_PREDICTION_DISABLED_REQUEST = 4043;

    /**
     * The Constant OPUS_GET_GAIN_REQUEST.
     */
    int OPUS_GET_GAIN_REQUEST = 4045;

    /**
     * The Constant OPUS_BANDWIDTH_WIDEBAND.
     */
    int OPUS_BANDWIDTH_WIDEBAND = 1103;

    /**
     * The Constant OPUS_INVALID_PACKET.
     */
    int OPUS_INVALID_PACKET = -4;

    /**
     * The Constant OPUS_ALLOC_FAIL.
     */
    int OPUS_ALLOC_FAIL = -7;

    /**
     * The Constant OPUS_OK.
     */
    int OPUS_OK = 0;

    /**
     * The Constant OPUS_MULTISTREAM_GET_DECODER_STATE_REQUEST.
     */
    int OPUS_MULTISTREAM_GET_DECODER_STATE_REQUEST = 5122;

    /**
     * The Constant OPUS_MULTISTREAM_GET_ENCODER_STATE_REQUEST.
     */
    int OPUS_MULTISTREAM_GET_ENCODER_STATE_REQUEST = 5120;

    /**
     * Opus encoder get size.
     *
     * @param channels the channels
     * @return the int
     */
    int opus_encoder_get_size(int channels);

    /**
     * Opus encoder create.
     *
     * @param Fs          the fs
     * @param channels    the channels
     * @param application the application
     * @param error       the error
     * @return the pointer by reference
     */
    PointerByReference opus_encoder_create(int Fs, int channels, int application, IntBuffer error);

    /**
     * Opus encoder init.
     *
     * @param st          the st
     * @param Fs          the fs
     * @param channels    the channels
     * @param application the application
     * @return the int
     */
    int opus_encoder_init(PointerByReference st, int Fs, int channels, int application);

    /**
     * Opus encode.
     *
     * @param st             the st
     * @param pcm            the pcm
     * @param frame_size     the frame size
     * @param data           the data
     * @param max_data_bytes the max data bytes
     * @return the int
     */
    int opus_encode(PointerByReference st, ShortBuffer pcm, int frame_size, ByteBuffer data, int max_data_bytes);

    /**
     * Opus encode.
     *
     * @param st             the st
     * @param pcm            the pcm
     * @param frame_size     the frame size
     * @param data           the data
     * @param max_data_bytes the max data bytes
     * @return the int
     */
    int opus_encode(PointerByReference st, ShortByReference pcm, int frame_size, Pointer data, int max_data_bytes);

    /**
     * Opus encode float.
     *
     * @param st             the st
     * @param pcm            the pcm
     * @param frame_size     the frame size
     * @param data           the data
     * @param max_data_bytes the max data bytes
     * @return the int
     */
    int opus_encode_float(PointerByReference st, float[] pcm, int frame_size, ByteBuffer data, int max_data_bytes);

    /**
     * Opus encode float.
     *
     * @param st             the st
     * @param pcm            the pcm
     * @param frame_size     the frame size
     * @param data           the data
     * @param max_data_bytes the max data bytes
     * @return the int
     */
    int opus_encode_float(PointerByReference st, FloatByReference pcm, int frame_size, Pointer data, int max_data_bytes);

    /**
     * Opus encoder destroy.
     *
     * @param st the st
     */
    void opus_encoder_destroy(PointerByReference st);

    /**
     * Opus encoder ctl.
     *
     * @param st      the st
     * @param request the request
     * @param varargs the varargs
     * @return the int
     */
    int opus_encoder_ctl(PointerByReference st, int request, Object... varargs);

    /**
     * Opus decoder get size.
     *
     * @param channels the channels
     * @return the int
     */
    int opus_decoder_get_size(int channels);

    /**
     * Opus decoder create.
     *
     * @param Fs       the fs
     * @param channels the channels
     * @param error    the error
     * @return the pointer by reference
     */
    PointerByReference opus_decoder_create(int Fs, int channels, IntBuffer error);

    /**
     * Opus decoder init.
     *
     * @param st       the st
     * @param Fs       the fs
     * @param channels the channels
     * @return the int
     */
    int opus_decoder_init(PointerByReference st, int Fs, int channels);

    /**
     * Opus decode.
     *
     * @param st         the st
     * @param data       the data
     * @param len        the len
     * @param pcm        the pcm
     * @param frame_size the frame size
     * @param decode_fec the decode fec
     * @return the int
     */
    int opus_decode(PointerByReference st, byte[] data, int len, ShortBuffer pcm, int frame_size, int decode_fec);

    /**
     * Opus decode.
     *
     * @param st         the st
     * @param data       the data
     * @param len        the len
     * @param pcm        the pcm
     * @param frame_size the frame size
     * @param decode_fec the decode fec
     * @return the int
     */
    int opus_decode(PointerByReference st, Pointer data, int len, ShortByReference pcm, int frame_size, int decode_fec);

    /**
     * Opus decode float.
     *
     * @param st         the st
     * @param data       the data
     * @param len        the len
     * @param pcm        the pcm
     * @param frame_size the frame size
     * @param decode_fec the decode fec
     * @return the int
     */
    int opus_decode_float(PointerByReference st, byte[] data, int len, FloatBuffer pcm, int frame_size, int decode_fec);

    /**
     * Opus decode float.
     *
     * @param st         the st
     * @param data       the data
     * @param len        the len
     * @param pcm        the pcm
     * @param frame_size the frame size
     * @param decode_fec the decode fec
     * @return the int
     */
    int opus_decode_float(PointerByReference st, Pointer data, int len, FloatByReference pcm, int frame_size,
                          int decode_fec);

    /**
     * Opus decoder ctl.
     *
     * @param st      the st
     * @param request the request
     * @param varargs the varargs
     * @return the int
     */
    int opus_decoder_ctl(PointerByReference st, int request, Object... varargs);

    /**
     * Opus decoder destroy.
     *
     * @param st the st
     */
    void opus_decoder_destroy(PointerByReference st);

    /**
     * Opus packet parse.
     *
     * @param data           the data
     * @param len            the len
     * @param out_toc        the out toc
     * @param frames         the frames
     * @param size           the size
     * @param payload_offset the payload offset
     * @return the int
     */
    int opus_packet_parse(byte[] data, int len, ByteBuffer out_toc, byte[] frames, ShortBuffer size,
                          IntBuffer payload_offset);

    /**
     * Opus packet get bandwidth.
     *
     * @param data the data
     * @return the int
     */
    int opus_packet_get_bandwidth(byte[] data);

    /**
     * Opus packet get samples per frame.
     *
     * @param data the data
     * @param Fs   the fs
     * @return the int
     */
    int opus_packet_get_samples_per_frame(byte[] data, int Fs);

    /**
     * Opus packet get nb channels.
     *
     * @param data the data
     * @return the int
     */
    int opus_packet_get_nb_channels(byte[] data);

    /**
     * Opus packet get nb frames.
     *
     * @param packet the packet
     * @param len    the len
     * @return the int
     */
    int opus_packet_get_nb_frames(byte[] packet, int len);

    /**
     * Opus packet get nb samples.
     *
     * @param packet the packet
     * @param len    the len
     * @param Fs     the fs
     * @return the int
     */
    int opus_packet_get_nb_samples(byte[] packet, int len, int Fs);

    /**
     * Opus decoder get nb samples.
     *
     * @param dec    the dec
     * @param packet the packet
     * @param len    the len
     * @return the int
     */
    int opus_decoder_get_nb_samples(PointerByReference dec, byte[] packet, int len);

    /**
     * Opus decoder get nb samples.
     *
     * @param dec    the dec
     * @param packet the packet
     * @param len    the len
     * @return the int
     */
    int opus_decoder_get_nb_samples(PointerByReference dec, Pointer packet, int len);

    /**
     * Opus pcm soft clip.
     *
     * @param pcm          the pcm
     * @param frame_size   the frame size
     * @param channels     the channels
     * @param softclip_mem the softclip mem
     */
    void opus_pcm_soft_clip(FloatBuffer pcm, int frame_size, int channels, FloatBuffer softclip_mem);

    /**
     * Opus repacketizer get size.
     *
     * @return the int
     */
    int opus_repacketizer_get_size();

    /**
     * Opus repacketizer init.
     *
     * @param rp the rp
     * @return the pointer by reference
     */
    PointerByReference opus_repacketizer_init(PointerByReference rp);

    /**
     * Opus repacketizer create.
     *
     * @return the pointer by reference
     */
    PointerByReference opus_repacketizer_create();

    /**
     * Opus repacketizer destroy.
     *
     * @param rp the rp
     */
    void opus_repacketizer_destroy(PointerByReference rp);

    /**
     * Opus repacketizer cat.
     *
     * @param rp   the rp
     * @param data the data
     * @param len  the len
     * @return the int
     */
    int opus_repacketizer_cat(PointerByReference rp, byte[] data, int len);

    /**
     * Opus repacketizer cat.
     *
     * @param rp   the rp
     * @param data the data
     * @param len  the len
     * @return the int
     */
    int opus_repacketizer_cat(PointerByReference rp, Pointer data, int len);

    /**
     * Opus repacketizer out range.
     *
     * @param rp     the rp
     * @param begin  the begin
     * @param end    the end
     * @param data   the data
     * @param maxlen the maxlen
     * @return the int
     */
    int opus_repacketizer_out_range(PointerByReference rp, int begin, int end, ByteBuffer data, int maxlen);

    /**
     * Opus repacketizer out range.
     *
     * @param rp     the rp
     * @param begin  the begin
     * @param end    the end
     * @param data   the data
     * @param maxlen the maxlen
     * @return the int
     */
    int opus_repacketizer_out_range(PointerByReference rp, int begin, int end, Pointer data, int maxlen);

    /**
     * Opus repacketizer get nb frames.
     *
     * @param rp the rp
     * @return the int
     */
    int opus_repacketizer_get_nb_frames(PointerByReference rp);

    /**
     * Opus repacketizer out.
     *
     * @param rp     the rp
     * @param data   the data
     * @param maxlen the maxlen
     * @return the int
     */
    int opus_repacketizer_out(PointerByReference rp, ByteBuffer data, int maxlen);

    /**
     * Opus repacketizer out.
     *
     * @param rp     the rp
     * @param data   the data
     * @param maxlen the maxlen
     * @return the int
     */
    int opus_repacketizer_out(PointerByReference rp, Pointer data, int maxlen);

    /**
     * Opus packet pad.
     *
     * @param data    the data
     * @param len     the len
     * @param new_len the new len
     * @return the int
     */
    int opus_packet_pad(ByteBuffer data, int len, int new_len);

    /**
     * Opus packet unpad.
     *
     * @param data the data
     * @param len  the len
     * @return the int
     */
    int opus_packet_unpad(ByteBuffer data, int len);

    /**
     * Opus multistream packet pad.
     *
     * @param data       the data
     * @param len        the len
     * @param new_len    the new len
     * @param nb_streams the nb streams
     * @return the int
     */
    int opus_multistream_packet_pad(ByteBuffer data, int len, int new_len, int nb_streams);

    /**
     * Opus multistream packet unpad.
     *
     * @param data       the data
     * @param len        the len
     * @param nb_streams the nb streams
     * @return the int
     */
    int opus_multistream_packet_unpad(ByteBuffer data, int len, int nb_streams);

    /**
     * Opus strerror.
     *
     * @param error the error
     * @return the string
     */
    String opus_strerror(int error);

  /**
     * Opus get version string.
     *
     * @return the string
     */
    String opus_get_version_string();

  /**
     * Opus multistream encoder get size.
     *
     * @param streams         the streams
     * @param coupled_streams the coupled streams
     * @return the int
     */
    int opus_multistream_encoder_get_size(int streams, int coupled_streams);

  /**
     * Opus multistream surround encoder get size.
     *
     * @param channels       the channels
     * @param mapping_family the mapping family
     * @return the int
     */
    int opus_multistream_surround_encoder_get_size(int channels, int mapping_family);

    /**
     * Opus multistream encoder create.
     *
     * @param Fs              the fs
     * @param channels        the channels
     * @param streams         the streams
     * @param coupled_streams the coupled streams
     * @param mapping         the mapping
     * @param application     the application
     * @param error           the error
     * @return the pointer by reference
     */
    PointerByReference opus_multistream_encoder_create(int Fs, int channels, int streams, int coupled_streams,
                                                       byte[] mapping, int application, IntBuffer error);

    // ******************** Multi Stream Support

    /**
     * Opus multistream surround encoder create.
     *
     * @param Fs              the fs
     * @param channels        the channels
     * @param mapping_family  the mapping family
     * @param streams         the streams
     * @param coupled_streams the coupled streams
     * @param mapping         the mapping
     * @param application     the application
     * @param error           the error
     * @return the pointer by reference
     */
    PointerByReference opus_multistream_surround_encoder_create(int Fs, int channels, int mapping_family,
                                                                IntBuffer streams, IntBuffer coupled_streams, ByteBuffer mapping, int application, IntBuffer error);

    /**
     * Opus multistream encoder init.
     *
     * @param st              the st
     * @param Fs              the fs
     * @param channels        the channels
     * @param streams         the streams
     * @param coupled_streams the coupled streams
     * @param mapping         the mapping
     * @param application     the application
     * @return the int
     */
    int opus_multistream_encoder_init(PointerByReference st, int Fs, int channels, int streams, int coupled_streams,
                                      byte[] mapping, int application);

    /**
     * Opus multistream encoder init.
     *
     * @param st              the st
     * @param Fs              the fs
     * @param channels        the channels
     * @param streams         the streams
     * @param coupled_streams the coupled streams
     * @param mapping         the mapping
     * @param application     the application
     * @return the int
     */
    int opus_multistream_encoder_init(PointerByReference st, int Fs, int channels, int streams, int coupled_streams,
                                      Pointer mapping, int application);

    /**
     * Opus multistream surround encoder init.
     *
     * @param st              the st
     * @param Fs              the fs
     * @param channels        the channels
     * @param mapping_family  the mapping family
     * @param streams         the streams
     * @param coupled_streams the coupled streams
     * @param mapping         the mapping
     * @param application     the application
     * @return the int
     */
    int opus_multistream_surround_encoder_init(PointerByReference st, int Fs, int channels, int mapping_family,
                                               IntBuffer streams, IntBuffer coupled_streams, ByteBuffer mapping, int application);

    /**
     * Opus multistream surround encoder init.
     *
     * @param st              the st
     * @param Fs              the fs
     * @param channels        the channels
     * @param mapping_family  the mapping family
     * @param streams         the streams
     * @param coupled_streams the coupled streams
     * @param mapping         the mapping
     * @param application     the application
     * @return the int
     */
    int opus_multistream_surround_encoder_init(PointerByReference st, int Fs, int channels, int mapping_family,
                                               IntByReference streams, IntByReference coupled_streams, Pointer mapping, int application);

    /**
     * Opus multistream encode.
     *
     * @param st             the st
     * @param pcm            the pcm
     * @param frame_size     the frame size
     * @param data           the data
     * @param max_data_bytes the max data bytes
     * @return the int
     */
    int opus_multistream_encode(PointerByReference st, ShortBuffer pcm, int frame_size, ByteBuffer data,
                                int max_data_bytes);

    /**
     * Opus multistream encode.
     *
     * @param st             the st
     * @param pcm            the pcm
     * @param frame_size     the frame size
     * @param data           the data
     * @param max_data_bytes the max data bytes
     * @return the int
     */
    int opus_multistream_encode(PointerByReference st, ShortByReference pcm, int frame_size, Pointer data,
                                int max_data_bytes);

    /**
     * Opus multistream encode float.
     *
     * @param st             the st
     * @param pcm            the pcm
     * @param frame_size     the frame size
     * @param data           the data
     * @param max_data_bytes the max data bytes
     * @return the int
     */
    int opus_multistream_encode_float(PointerByReference st, float[] pcm, int frame_size, ByteBuffer data,
                                      int max_data_bytes);

    /**
     * Opus multistream encode float.
     *
     * @param st             the st
     * @param pcm            the pcm
     * @param frame_size     the frame size
     * @param data           the data
     * @param max_data_bytes the max data bytes
     * @return the int
     */
    int opus_multistream_encode_float(PointerByReference st, FloatByReference pcm, int frame_size, Pointer data,
                                      int max_data_bytes);

    /**
     * Opus multistream encoder destroy.
     *
     * @param st the st
     */
    void opus_multistream_encoder_destroy(PointerByReference st);

    /**
     * Opus multistream encoder ctl.
     *
     * @param st      the st
     * @param request the request
     * @param varargs the varargs
     * @return the int
     */
    int opus_multistream_encoder_ctl(PointerByReference st, int request, Object... varargs);

    /**
     * Opus multistream decoder get size.
     *
     * @param streams         the streams
     * @param coupled_streams the coupled streams
     * @return the int
     */
    int opus_multistream_decoder_get_size(int streams, int coupled_streams);

    /**
     * Opus multistream decoder create.
     *
     * @param Fs              the fs
     * @param channels        the channels
     * @param streams         the streams
     * @param coupled_streams the coupled streams
     * @param mapping         the mapping
     * @param error           the error
     * @return the pointer by reference
     */
    PointerByReference opus_multistream_decoder_create(int Fs, int channels, int streams, int coupled_streams,
                                                       byte[] mapping, IntBuffer error);

    /**
     * Opus multistream decoder init.
     *
     * @param st              the st
     * @param Fs              the fs
     * @param channels        the channels
     * @param streams         the streams
     * @param coupled_streams the coupled streams
     * @param mapping         the mapping
     * @return the int
     */
    int opus_multistream_decoder_init(PointerByReference st, int Fs, int channels, int streams, int coupled_streams,
                                      byte[] mapping);

    /**
     * Opus multistream decoder init.
     *
     * @param st              the st
     * @param Fs              the fs
     * @param channels        the channels
     * @param streams         the streams
     * @param coupled_streams the coupled streams
     * @param mapping         the mapping
     * @return the int
     */
    int opus_multistream_decoder_init(PointerByReference st, int Fs, int channels, int streams, int coupled_streams,
                                      Pointer mapping);

    /**
     * Opus multistream decode.
     *
     * @param st         the st
     * @param data       the data
     * @param len        the len
     * @param pcm        the pcm
     * @param frame_size the frame size
     * @param decode_fec the decode fec
     * @return the int
     */
    int opus_multistream_decode(PointerByReference st, byte[] data, int len, ShortBuffer pcm, int frame_size,
                                int decode_fec);

    /**
     * Opus multistream decode.
     *
     * @param st         the st
     * @param data       the data
     * @param len        the len
     * @param pcm        the pcm
     * @param frame_size the frame size
     * @param decode_fec the decode fec
     * @return the int
     */
    int opus_multistream_decode(PointerByReference st, Pointer data, int len, ShortByReference pcm, int frame_size,
                                int decode_fec);

    /**
     * Opus multistream decode float.
     *
     * @param st         the st
     * @param data       the data
     * @param len        the len
     * @param pcm        the pcm
     * @param frame_size the frame size
     * @param decode_fec the decode fec
     * @return the int
     */
    int opus_multistream_decode_float(PointerByReference st, byte[] data, int len, FloatBuffer pcm, int frame_size,
                                      int decode_fec);

    /**
     * Opus multistream decode float.
     *
     * @param st         the st
     * @param data       the data
     * @param len        the len
     * @param pcm        the pcm
     * @param frame_size the frame size
     * @param decode_fec the decode fec
     * @return the int
     */
    int opus_multistream_decode_float(PointerByReference st, Pointer data, int len, FloatByReference pcm, int frame_size,
                                      int decode_fec);

    /**
     * Opus multistream decoder ctl.
     *
     * @param st      the st
     * @param request the request
     * @param varargs the varargs
     * @return the int
     */
    int opus_multistream_decoder_ctl(PointerByReference st, int request, Object... varargs);

    /**
     * Opus multistream decoder destroy.
     *
     * @param st the st
     */
    void opus_multistream_decoder_destroy(PointerByReference st);

    /**
     * Opus custom mode create.
     *
     * @param Fs         the fs
     * @param frame_size the frame size
     * @param error      the error
     * @return the pointer by reference
     */
    PointerByReference opus_custom_mode_create(int Fs, int frame_size, IntBuffer error);

    /**
     * Opus custom mode destroy.
     *
     * @param mode the mode
     */
    void opus_custom_mode_destroy(PointerByReference mode);

    /**
     * Opus custom encoder get size.
     *
     * @param mode     the mode
     * @param channels the channels
     * @return the int
     */
    int opus_custom_encoder_get_size(PointerByReference mode, int channels);

    /**
     * Opus custom encoder create.
     *
     * @param mode     the mode
     * @param channels the channels
     * @param error    the error
     * @return the pointer by reference
     */
    PointerByReference opus_custom_encoder_create(PointerByReference mode, int channels, IntBuffer error);

  /**
     * Opus custom encoder create.
     *
     * @param mode     the mode
     * @param channels the channels
     * @param error    the error
     * @return the pointer by reference
     */
    PointerByReference opus_custom_encoder_create(PointerByReference mode, int channels, IntByReference error);

  // ******************** Custom Support

    /**
     * Opus custom encoder destroy.
     *
     * @param st the st
     */
    void opus_custom_encoder_destroy(PointerByReference st);

    /**
     * Opus custom encode float.
     *
     * @param st                 the st
     * @param pcm                the pcm
     * @param frame_size         the frame size
     * @param compressed         the compressed
     * @param maxCompressedBytes the max compressed bytes
     * @return the int
     */
    int opus_custom_encode_float(PointerByReference st, float[] pcm, int frame_size, ByteBuffer compressed,
                                 int maxCompressedBytes);

    /**
     * Opus custom encode float.
     *
     * @param st                 the st
     * @param pcm                the pcm
     * @param frame_size         the frame size
     * @param compressed         the compressed
     * @param maxCompressedBytes the max compressed bytes
     * @return the int
     */
    int opus_custom_encode_float(PointerByReference st, FloatByReference pcm, int frame_size, Pointer compressed,
                                 int maxCompressedBytes);

    /**
     * Opus custom encode.
     *
     * @param st                 the st
     * @param pcm                the pcm
     * @param frame_size         the frame size
     * @param compressed         the compressed
     * @param maxCompressedBytes the max compressed bytes
     * @return the int
     */
    int opus_custom_encode(PointerByReference st, ShortBuffer pcm, int frame_size, ByteBuffer compressed,
                           int maxCompressedBytes);

    /**
     * Opus custom encode.
     *
     * @param st                 the st
     * @param pcm                the pcm
     * @param frame_size         the frame size
     * @param compressed         the compressed
     * @param maxCompressedBytes the max compressed bytes
     * @return the int
     */
    int opus_custom_encode(PointerByReference st, ShortByReference pcm, int frame_size, Pointer compressed,
                           int maxCompressedBytes);

    /**
     * Opus custom encoder ctl.
     *
     * @param st      the st
     * @param request the request
     * @param varargs the varargs
     * @return the int
     */
    int opus_custom_encoder_ctl(PointerByReference st, int request, Object... varargs);

    /**
     * Opus custom decoder get size.
     *
     * @param mode     the mode
     * @param channels the channels
     * @return the int
     */
    int opus_custom_decoder_get_size(PointerByReference mode, int channels);

    /**
     * Opus custom decoder init.
     *
     * @param st       the st
     * @param mode     the mode
     * @param channels the channels
     * @return the int
     */
    int opus_custom_decoder_init(PointerByReference st, PointerByReference mode, int channels);

    /**
     * Opus custom decoder create.
     *
     * @param mode     the mode
     * @param channels the channels
     * @param error    the error
     * @return the pointer by reference
     */
    PointerByReference opus_custom_decoder_create(PointerByReference mode, int channels, IntBuffer error);

    /**
     * Opus custom decoder create.
     *
     * @param mode     the mode
     * @param channels the channels
     * @param error    the error
     * @return the pointer by reference
     */
    PointerByReference opus_custom_decoder_create(PointerByReference mode, int channels, IntByReference error);

    /**
     * Opus custom decoder destroy.
     *
     * @param st the st
     */
    void opus_custom_decoder_destroy(PointerByReference st);

    /**
     * Opus custom decode float.
     *
     * @param st         the st
     * @param data       the data
     * @param len        the len
     * @param pcm        the pcm
     * @param frame_size the frame size
     * @return the int
     */
    int opus_custom_decode_float(PointerByReference st, byte[] data, int len, FloatBuffer pcm, int frame_size);

    /**
     * Opus custom decode float.
     *
     * @param st         the st
     * @param data       the data
     * @param len        the len
     * @param pcm        the pcm
     * @param frame_size the frame size
     * @return the int
     */
    int opus_custom_decode_float(PointerByReference st, Pointer data, int len, FloatByReference pcm, int frame_size);

    /**
     * Opus custom decode.
     *
     * @param st         the st
     * @param data       the data
     * @param len        the len
     * @param pcm        the pcm
     * @param frame_size the frame size
     * @return the int
     */
    int opus_custom_decode(PointerByReference st, byte[] data, int len, ShortBuffer pcm, int frame_size);

    /**
     * Opus custom decode.
     *
     * @param st         the st
     * @param data       the data
     * @param len        the len
     * @param pcm        the pcm
     * @param frame_size the frame size
     * @return the int
     */
    int opus_custom_decode(PointerByReference st, Pointer data, int len, ShortByReference pcm, int frame_size);

    /**
     * Opus custom decoder ctl.
     *
     * @param st      the st
     * @param request the request
     * @param varargs the varargs
     * @return the int
     */
    int opus_custom_decoder_ctl(PointerByReference st, int request, Object... varargs);

    /**
     * The Class OpusDecoder.
     */
    class OpusDecoder extends PointerType {

        /**
         * Instantiates a new opus decoder.
         *
         * @param address the address
         */
        public OpusDecoder(Pointer address) {
            super(address);
        }

        /**
         * Instantiates a new opus decoder.
         */
        public OpusDecoder() {
            super();
        }
    }

    /**
     * The Class OpusEncoder.
     */
    class OpusEncoder extends PointerType {

        /**
         * Instantiates a new opus encoder.
         *
         * @param address the address
         */
        public OpusEncoder(Pointer address) {
            super(address);
        }

        /**
         * Instantiates a new opus encoder.
         */
        public OpusEncoder() {
            super();
        }
    }

    /**
     * The Class OpusRepacketizer.
     */
    class OpusRepacketizer extends PointerType {

        /**
         * Instantiates a new opus repacketizer.
         *
         * @param address the address
         */
        public OpusRepacketizer(Pointer address) {
            super(address);
        }

        /**
         * Instantiates a new opus repacketizer.
         */
        public OpusRepacketizer() {
            super();
        }
    }

    /**
     * The Class OpusMSEncoder.
     */
    class OpusMSEncoder extends PointerType {

        /**
         * Instantiates a new opus MS encoder.
         *
         * @param address the address
         */
        public OpusMSEncoder(Pointer address) {
            super(address);
        }

        /**
         * Instantiates a new opus MS encoder.
         */
        public OpusMSEncoder() {
            super();
        }
    }

    /**
     * The Class OpusMSDecoder.
     */
    class OpusMSDecoder extends PointerType {

        /**
         * Instantiates a new opus MS decoder.
         *
         * @param address the address
         */
        public OpusMSDecoder(Pointer address) {
            super(address);
        }

        /**
         * Instantiates a new opus MS decoder.
         */
        public OpusMSDecoder() {
            super();
        }
    }

    /**
     * The Class OpusCustomDecoder.
     */
    class OpusCustomDecoder extends PointerType {

        /**
         * Instantiates a new opus custom decoder.
         *
         * @param address the address
         */
        public OpusCustomDecoder(Pointer address) {
            super(address);
        }

        /**
         * Instantiates a new opus custom decoder.
         */
        public OpusCustomDecoder() {
            super();
        }
    }

  /**
     * The Class OpusCustomEncoder.
     */
    class OpusCustomEncoder extends PointerType {

        /**
         * Instantiates a new opus custom encoder.
         *
         * @param address the address
         */
        public OpusCustomEncoder(Pointer address) {
            super(address);
        }

        /**
         * Instantiates a new opus custom encoder.
         */
        public OpusCustomEncoder() {
            super();
        }
    }

  /**
     * The Class OpusCustomMode.
     */
    class OpusCustomMode extends PointerType {

        /**
         * Instantiates a new opus custom mode.
         *
         * @param address the address
         */
        public OpusCustomMode(Pointer address) {
            super(address);
        }

        /**
         * Instantiates a new opus custom mode.
         */
        public OpusCustomMode() {
            super();
        }
    }

}
